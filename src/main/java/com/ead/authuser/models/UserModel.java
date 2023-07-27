package com.ead.authuser.models;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.validation.ValidationHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_USERS")
public class UserModel extends RepresentationModel<UserModel> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("userId")
    private UUID userId;
    @Column(nullable = false, unique = true, length = 50)
    @JsonProperty("username")
    private String username;
    @Column(nullable = false, unique = true, length = 50)
    @JsonProperty("email")
    private String email;
    @Column(nullable = false, length = 255)
    @JsonIgnore
    private String password;
    @Column(nullable = false, length = 150)
    @JsonProperty("fullName")
    private String fullName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonProperty("userStatus")
    private UserStatus userStatus;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonProperty("userType")
    private UserType userType;
    @Column(length = 20)
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @Column(length = 20)
    @JsonProperty("cpf")
    private String cpf;
    @Column
    @JsonProperty("imageUrl")
    private String imageUrl;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonProperty("creationDate")
    private LocalDateTime creationDate;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonProperty("lastUpdateDate")
    private LocalDateTime lastUpdateDate;


    protected UserModel(){}
    private UserModel(
            final String aUsername,
            final String aEmail,
            final String aPassword,
            final String aFullName,
            final UserStatus aUserStatus,
            final UserType aUserType,
            final String aPhoneNumber,
            final String aCpf,
            final String aImageUrl,
            final LocalDateTime aCreationDate,
            final LocalDateTime aLastUpdateDate
    ) {
//        this.userId = UUID.randomUUID();
        this.username = aUsername;
        this.email = aEmail;
        this.password = aPassword;
        this.fullName = aFullName;
        this.userStatus = aUserStatus;
        this.userType = aUserType;
        this.phoneNumber = aPhoneNumber;
        this.cpf = aCpf;
        this.imageUrl = aImageUrl;
        this.creationDate = Objects.requireNonNull(aCreationDate, "'creationDate' should not be null");
        this.lastUpdateDate = Objects.requireNonNull(aLastUpdateDate, "'lastUpdateDate' should not be null");
    }


    public static UserModel with(
            final String userName,
            final String email,
            final String password,
            final String fullName,
            final UserStatus userStatus,
            final UserType userType,
            final String phoneNumber,
            final String cpf,
            final String imageUrl,
            final LocalDateTime creationDate,
            final LocalDateTime lastUpdateDate
    ) {
        return new UserModel(
                userName,
                email,
                password,
                fullName,
                userStatus,
                userType,
                phoneNumber,
                cpf,
                imageUrl,
                creationDate,
                lastUpdateDate
        );
    }

    public static UserModel with(final UserModel aUserModel) {
        return with(
                aUserModel.username,
                aUserModel.email,
                aUserModel.password,
                aUserModel.fullName,
                aUserModel.userStatus,
                aUserModel.userType,
                aUserModel.phoneNumber,
                aUserModel.cpf,
                aUserModel.imageUrl,
                aUserModel.creationDate,
                aUserModel.lastUpdateDate

        );
    }


    public void validate(final ValidationHandler handler) {
        new UserModelValidator(this, handler).validate();
    }




}
