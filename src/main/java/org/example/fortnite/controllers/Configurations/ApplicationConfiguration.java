package org.example.fortnite.controllers.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class ApplicationConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}



//Verbindung mit url in application
// In Ihrer UserDetailsServiceImpl-Klasse wird die Datenbankverbindung hergestellt.
// Spring Data JPA ermöglicht dies durch die Integration von Repositories, die Methoden für den Datenbankzugriff bereitstellen.
// Als nächstes gehen wir in das Repository
// Und nun gehen wir in die JWTAuthenticationfilter hier erfoglt die Datenbankkommunikation nach einer erfolgreichen Authentifizirung
// Nun können abfragen im UserService Skin/Weapon gemacht werden
// MVC ist ein entwurfmuster zur unterteilung und Organisation von Software code
// Model/View/Controller
// Gradle ist ein Biuld tool, wie z.b Maven ant.