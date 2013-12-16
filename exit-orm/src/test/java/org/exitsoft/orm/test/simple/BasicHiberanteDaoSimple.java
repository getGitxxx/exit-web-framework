package org.exitsoft.orm.test.simple;

import org.exitsoft.orm.core.hibernate.support.HibernateSupportDao;
import org.exitsoft.orm.test.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class BasicHiberanteDaoSimple extends HibernateSupportDao<User, String>{

}
