package com.dasunhq.spring.mockito.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.dasunhq.spring.mockito.api.dao.UserRepository;
import com.dasunhq.spring.mockito.api.model.User;
import com.dasunhq.spring.mockito.api.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMockitoApplicationTests {

    @Autowired
	private UserService service;

    @MockBean
    private UserRepository repository;

    @Test
    public void getUsersTest(){
        when(repository.findAll()).thenReturn(Stream
                .of(new User(376, "Daniel", 31, "USA"),
                        new User(855,"Huy",35, "UK"))
                .collect(Collectors.toList()));
        assertEquals(2, service.getUsers().size());
    }

    @Test
    public void getUserByAddressTest(){
        String address = "Colombo";

        when(repository.findByAddress(address))
                .thenReturn(
                        Stream.of(new User(376, "Daniel", 31, "USA")).collect(Collectors.toList())
                );
        assertNotEquals("Users with the given address should match", address, service.getUserbyAddress(address).get(0).getAddress());
    }


    @Test
    public void saveUserTest(){
        User user = new User(376, "Daniel", 31, "USA");
        when(repository.save(user)).thenReturn(user);
        assertEquals(user,service.addUser(user));
    }

    @Test
    public void deleteUserTest(){
        User user = new User(376, "Daniel", 31, "USA");
        service.deleteUser(user);
        verify(repository, times(1)).delete(user);
    }

}
