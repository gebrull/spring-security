package com.example.security;

import com.example.security.repository.UserRepository;
import com.example.security.services.SSUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration          //Fala pro spring que esta é uma classe que contem configuração
@EnableWebSecurity      //Fala pro spring que esta classe contem segurança para web
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    
    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){  //Vai fazer um encoder do password
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private SSUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception{
        return new SSUserDetailsService(userRepository);
    }

    // Esse método aqui usa uma login page "padrão do spring"
    // @Override
    // protected void configure(HttpSecurity http) throws Exception{ // diz que qualquer request autenticada vai para o formLogin
    //     http.authorizeRequests().anyRequest().authenticated().and().formLogin();
    // }

    // Esse método trata todo mundo como o mesmo nível de autoridade
    // @Override
    // protected void configure(HttpSecurity http) throws Exception{ // diz que qualquer request autenticada vai para a loginPage definida
    //     http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll();
    // }


    @Override
    protected void configure(HttpSecurity http) throws Exception{ 
        http.authorizeRequests()
            .antMatchers("/", "/h2-console").permitAll()   
            .antMatchers("/admin").access("hasAuthority('ADMIN')")          // o mesmo para essa linha
            .anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))    //aqui basicamente nos envia para o logout
            .logoutSuccessUrl("/login").permitAll()    // quando o logout é bem sucedido, nos redireciona para o /login
            .and()
            .httpBasic();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    // Não usaremos esse método pq agora vamos usar a autenticação no bd
    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception{ 
    //     auth.inMemoryAuthentication()   //metodo para autenticar em memória os users e senhas
    //     .withUser("root").password(passwordEncoder().encode("root")).authorities("ADMIN")  //basicamente, em cada comando, adicionamos um usuário e senha que seriam válidos
    //     .and()
    //     .withUser("user").password(passwordEncoder().encode("senha")).authorities("USERS");

    // }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{ //metodo para escolher como autenticar os users e senhas
        
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());

    }


}
