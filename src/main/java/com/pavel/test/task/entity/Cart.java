package com.pavel.test.task.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;

@Data
@Entity
@Table(name = "t_cart")
public class Cart {
	
	@Id
    @Column(name = "cart_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartid;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private Set<Item> items;

    public Cart(Long cartid, User user, Set<Item> items) {
		this.cartid = cartid;
		this.user = user;
		this.items = items;
	}

	public void addItem(Item item) {
        items.add(item);
    }

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public Long getCartid() {
		return cartid;
	}

}

