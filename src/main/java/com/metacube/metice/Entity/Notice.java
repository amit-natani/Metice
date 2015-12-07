package com.metacube.metice.Entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This is entity class which maps Notice entity into database table notice
 * 
 * @name Notice
 * @author Banwari Kevat
 */
@Entity
@Table(name = "notice")
public class Notice {
	/* primary key */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_id")
	int noticeId;

	@Column(name = "title", nullable = false)
	String title;

	@Column(name = "content", nullable = false)
	String content;

	@Temporal(TemporalType.DATE)
	@Column(name = "post_date", nullable = false)
	Date postDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "last_edited_date", nullable = false)
	Date lastEditedDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "expire_date")
	Date expireDate;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "posted_by")
	User postedBy;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "last_edited_by")
	User lastEditedBy;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "company_id")
	Company company;

	@Column(name = "tag_list", nullable = false)
	String tagList;

	@Column(name = "is_archive", nullable = false)
	boolean isArchive;

	/* default constructor */
	public Notice() {

	}

	/**
	 * Method to get notice id of particular notice
	 * 
	 * @name getNoticeId
	 * @return noticeId : id of notice
	 */
	public int getNoticeId() {
		return noticeId;
	}

	/**
	 * Method to set notice id of particular notice
	 * 
	 * @name setNoticeId
	 * @param noticeId
	 *            : id of notice
	 */
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	/**
	 * Method to get notice title of particular notice
	 * 
	 * @name getTitle
	 * @return title : title of notice
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Method to set notice title of particular notice
	 * 
	 * @name setTitle
	 * @param title
	 *            : title of notice
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Method to get notice contents of particular notice
	 * 
	 * @name getContent
	 * @return content : content of notice
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Method to set notice contents of particular notice
	 * 
	 * @name setContent
	 * @param content
	 *            : content of notice
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Method to get post date of particular notice
	 * 
	 * @name getPostDate
	 * @return postDate : notice posted date
	 */
	public Date getPostDate() {
		return postDate;
	}

	/**
	 * Method to set post date of notice
	 * 
	 * @name setPostDate
	 * @param postDate
	 *            : notice posted date
	 * 
	 */
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	/**
	 * Method to get expire date of notice
	 * 
	 * @name getExpireDate
	 * @return expireDate : notice expire date
	 */
	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * Method to get user who post this notice
	 * 
	 * @name getPostedBy
	 * @return postedBy : user who post this notice
	 */
	public User getPostedBy() {
		return postedBy;
	}

	/**
	 * Method to set user who post this notice
	 * 
	 * @name setPostedBy
	 * @param postedBy
	 *            : user who post this notice
	 */
	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}

	/**
	 * Method to get concern company of notice
	 * 
	 * @name getCompany
	 * @return company : concern company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Method to set concern company of notice
	 * 
	 * @name setCompany
	 * @param company
	 *            : concern company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Method to get tags of notice
	 * 
	 * @name getTagList
	 * @return tagList : comma separated tags
	 */
	public String getTagList() {
		return tagList;
	}

	/**
	 * Method to set tags of notice
	 * 
	 * @name setTagList
	 * @param tagList
	 *            : comma separated tags
	 * 
	 */
	public void setTagList(String tagList) {
		this.tagList = tagList;
	}

	/**
	 * Method to get status of notice in term of archive
	 * 
	 * @name isArchive
	 * @return isArchive : boolean value
	 */
	public boolean isArchive() {
		return isArchive;
	}

	/**
	 * Method to set status of notice in term of archive
	 * 
	 * @name setArchive
	 * @param isArchive
	 *            : boolean value
	 */
	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public User getLastEditedBy() {
		return lastEditedBy;
	}

	public void setLastEditedBy(User lastEditedBy) {
		this.lastEditedBy = lastEditedBy;
	}

	public Date getLastEditedDate() {
		return lastEditedDate;
	}

	public void setLastEditedDate(Date lastEditedDate) {
		this.lastEditedDate = lastEditedDate;
	}
	
}
