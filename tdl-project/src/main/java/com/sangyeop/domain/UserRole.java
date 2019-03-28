package com.sangyeop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author hagome
 * @since  2019-03-29
 */
@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "rno")
@ToString
public class UserRole {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long rno;

        private String roleName;
}
