package hr.fer.zemris.java.hw15.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents a single comment on a blog entry.
 * 
 * @author Patrik
 *
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**
	 * The ID
	 */
	private Long id;
	
	/**
	 * The parent blog entry
	 */
	private BlogEntry blogEntry;
	
	/**
	 * User e-mail address
	 */
	private String usersEMail;
	
	/**
	 * The message
	 */
	private String message;
	
	/**
	 * The date of the comment
	 */
	private Date postedOn;
	
	/**
	 * Returns the id
	 * @return the id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the id
	 * @param id id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the blog entry
	 * @return blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Sets the blog entry
	 * @param blogEntry blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Returns the user e-mail
	 * @return the user e-mail
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets the user e-mail
	 * @param usersEMail the user e-mail
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Returns the message
	 * @return the message
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message
	 * @param message the message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the posted on date
	 * @return the posted on date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets the posted on date
	 * @param postedOn the posted on date
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}