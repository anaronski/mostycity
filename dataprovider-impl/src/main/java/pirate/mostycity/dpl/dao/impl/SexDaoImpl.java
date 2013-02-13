package pirate.mostycity.dpl.dao.impl;

import org.springframework.stereotype.Repository;

import pirate.mostycity.dpl.dao.ISexDao;
import pirate.mostycity.dpl.entity.Sex;

@Repository
public class SexDaoImpl extends BaseDaoImpl<Sex, Long> implements ISexDao{

	public SexDaoImpl() {
		super(Sex.class);
	}

}
