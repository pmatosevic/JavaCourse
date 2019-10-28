package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name="BlogUser.queryNick", query="select u from BlogUser as u where u.nick=:nick"),
	@NamedQuery(name="BlogUser.queryAll", query="select u from BlogUser as u"),
	@NamedQuery(name="BlogUser.queryEntries", query="select b from BlogEntry as b where b.creator=:creator")
})
@Entity
@Table(name="blog_users")
@Cacheable(true)
public class BlogUser {

	/**
	 * The ID
	 */
	@Id @GeneratedValue
	private int id;
	
	/**
	 * The first name
	 */
	@Column(length=200, nullable=false)
	private String firstName;
	
	/**
	 * The last name
	 */
	@Column(length=200, nullable=false)
	private String lastName;
	
	/**
	 * The nickname
	 */
	@Column(length=200, nullable=false, unique=true)
	private String nick;
	
	/**
	 * The user e-mail
	 */
	@Column(length=200, nullable=false)
	private String email;
	
	/**
	 * The password hash
	 */
	@Column(length=200, nullable=false)
	private String passwordHash;

	/**
	 * The list of all blog posts the user has created
	 */
	@OneToMany(mappedBy="creator", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	private List<BlogEntry> blogEntries = new ArrayList<>();
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password hash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash the password hash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * @return the blog entries
	 */
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * @param blogEntries the blog entries to set
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}
	
	
}
