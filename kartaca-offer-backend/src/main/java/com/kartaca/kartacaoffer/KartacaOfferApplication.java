package com.kartaca.kartacaoffer;

import com.kartaca.kartacaoffer.entities.Product;
import com.kartaca.kartacaoffer.entities.User;
import com.kartaca.kartacaoffer.services.ProductService;
import com.kartaca.kartacaoffer.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KartacaOfferApplication {

    public static void main(String[] args) {
        SpringApplication.run(KartacaOfferApplication.class, args);
    }

    @Bean
    CommandLineRunner createSuperUser(UserService userService, ProductService productService) {
        return args -> {
            if (userService.userRetrieve(1L) == null) {
                User user = new User();
                user.setUsername("admin");
                user.setPassword("admin");
                user.setFirstName("root");
                user.setLastName("root");
                userService.createUser(user);
                Product productOne = new Product();
                productOne.setName("Jordan 1 Dark Mocha");
                productOne.setText("Jordan 1 Dark Mocha");
                productOne.setPrice(350D);
                productOne.setImage("https://www.ercanwear.com/images/urunler/101_air-jordan-242_1.jpg");
                Product productTwo = new Product();
                productTwo.setName("Jordan 1 Chicago");
                productTwo.setText("Jordan 1 Chicago");
                productTwo.setPrice(450D);
                productTwo.setImage("https://images.solecollector.com/images/fl_lossy,q_auto/c_crop,h_1100,w_2000,x_0,y_509/gfloflodv5pwsgb5qise/air-jordan-1-high-lost-and-found-dz5485-612");
                Product productThree = new Product();
                productThree.setName("Jordan 1 Travis Scott");
                productThree.setText("Jordan 1 Travis Scott");
                productThree.setPrice(550D);
                productThree.setImage("https://www.highsnobiety.com/static-assets/thumbor/1Z18Fhuscarbw382HzvKtwm3TWk=/1600x1067/www.highsnobiety.com/static-assets/wp-content/uploads/2019/05/15171827/travis-scott-air-jordan-1-cactus-jack-best-instagram-sneakers-01.jpg");
                productService.productCreate(productOne);
                productService.productCreate(productTwo);
                productService.productCreate(productThree);
            } else System.out.println("Super user already exists");
        };
    }


}
