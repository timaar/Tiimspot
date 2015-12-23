package com.timaar.tiimspot.web.rest.dto;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * A DTO representing a user, with his authorities.
 */

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.timaar.tiimspot.domain.Adres;
import com.timaar.tiimspot.domain.Authority;
import com.timaar.tiimspot.domain.Persoon;
import com.timaar.tiimspot.domain.User;
public class UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 100;

    @Pattern(regexp = "^[a-z0-9]*$")
    @NotNull
    @Size(min = 1, max = 50)
    private String login;

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    private Set<String> authorities;
    
    private String ouderVoornaam, ouderNaam, ouderTelefoonnummer, ouderStraat, ouderHuisnummer, ouderBusnummer, ouderPostcode, ouderGemeente;
    private ZonedDateTime ouderGeboorteDatum, kindGeboorteDatum;    
    private String kindVoornaam, kindNaam, kindTelefoonnummer;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user.getLogin(), null, 
            user.getEmail(), user.getActivated(), user.getLangKey(),
            user.getAuthorities().stream().map(Authority::getName)
                .collect(Collectors.toSet()));
    }

    public UserDTO(String login, String password, String email, boolean activated, String langKey, Set<String> authorities) {

        this.login = login;
        this.password = password;
        this.email = email;
        this.activated = activated;
        this.langKey = langKey;
        this.authorities = authorities;
    }
    
    public UserDTO(String login, String password, String email, boolean activated, String langKey, Set<String> authorities,
    		String ouderVoornaam, String ouderNaam, String ouderTelefoonnummer, String ouderStraat, String ouderHuisnummer, String ouderBusnummer, String ouderPostcode, String ouderGemeente,
    		ZonedDateTime ouderGeboorteDatum, ZonedDateTime kindGeboorteDatum, String kindVoornaam, String kindNaam, String kindTelefoonnummer) {
       this(login, password, email, activated, langKey, authorities);
       this.ouderVoornaam = ouderVoornaam;
       this.ouderNaam = ouderNaam;
       this.ouderTelefoonnummer = ouderTelefoonnummer;
       this.ouderStraat = ouderStraat;
       this.ouderHuisnummer = ouderHuisnummer;
       this.ouderBusnummer = ouderBusnummer; 
       this.ouderPostcode = ouderPostcode;
       this.ouderGemeente = ouderGemeente;
       this.ouderGeboorteDatum = ouderGeboorteDatum;
       this.kindGeboorteDatum = kindGeboorteDatum;    
       this.kindVoornaam = kindVoornaam;
       this.kindNaam = kindNaam;
       this.kindTelefoonnummer = kindTelefoonnummer;
    }

    public String getOuderVoornaam() {
		return ouderVoornaam;
	}

	public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public String getOuderNaam() {
		return ouderNaam;
	}

	public String getOuderTelefoonnummer() {
		return ouderTelefoonnummer;
	}

	public String getOuderStraat() {
		return ouderStraat;
	}

	public String getOuderHuisnummer() {
		return ouderHuisnummer;
	}

	public String getOuderBusnummer() {
		return ouderBusnummer;
	}

	public String getOuderPostcode() {
		return ouderPostcode;
	}

	public String getOuderGemeente() {
		return ouderGemeente;
	}

	public ZonedDateTime getOuderGeboorteDatum() {
		return ouderGeboorteDatum;
	}

	public ZonedDateTime getKindGeboorteDatum() {
		return kindGeboorteDatum;
	}

	public String getKindVoornaam() {
		return kindVoornaam;
	}

	public String getKindNaam() {
		return kindNaam;
	}

	public String getKindTelefoonnummer() {
		return kindTelefoonnummer;
	}

	@Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", authorities=" + authorities +
            "}";
    }
}
