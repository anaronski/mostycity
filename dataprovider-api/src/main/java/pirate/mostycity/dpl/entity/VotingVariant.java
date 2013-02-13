package pirate.mostycity.dpl.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="voting_variants")
public class VotingVariant implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long votingId;
	private String variantName;
	private Long answersCount;
	
	@Id
	@GeneratedValue
	@Column(name="voting_variant_id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="voting_id")
	public Long getVotingId() {
		return votingId;
	}
	public void setVotingId(Long votingId) {
		this.votingId = votingId;
	}
	
	@Column(name="variant_name")
	public String getVariantName() {
		return variantName;
	}
	public void setVariantName(String variantName) {
		this.variantName = variantName;
	}
	
	@Column(name="answers_count")
	public Long getAnswersCount() {
		return answersCount;
	}
	public void setAnswersCount(Long answersCount) {
		this.answersCount = answersCount;
	}
}
