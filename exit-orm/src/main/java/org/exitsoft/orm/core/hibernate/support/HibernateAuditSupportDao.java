package org.exitsoft.orm.core.hibernate.support;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.AuditQueryCreator;

/**
 * 基于Hibernate events的审计功能封装的Dao类
 * 
 * @author vincent
 *
 * @param <T> ORM对象
 * @param <PK> 主键Id类型
 */
public class HibernateAuditSupportDao<T, PK extends Serializable> extends HibernateSupportDao<T, PK>{
	
	public HibernateAuditSupportDao(){
		
	}

	public HibernateAuditSupportDao(Class<T> entityClass){
		super(entityClass);
	}
	
	/**
	 * 获取审计读取器
	 * 
	 * @return {@link AuditReader}
	 */
	protected AuditReader getAuditReader() {
		return AuditReaderFactory.get(getSession());
	}
	
	/**
	 * 获取审计查询创建器者
	 * 
	 * @return {@link AuditQueryCreator}
	 */
	protected AuditQueryCreator getAuditQueryCreator() {
		return getAuditReader().createQuery();
	}
	
	protected AuditQuery createAuditQuery(Number revision) {
		return createAuditQuery(revision, false);
	}
	
	protected AuditQuery createAuditQuery(Number revision,boolean includeDeletions) {
		return getAuditQueryCreator().forEntitiesAtRevision(entityClass, getEntityName(), revision, includeDeletions);
	}
	
	public T find(PK primaryKey, Number revision) {
		return find(primaryKey, revision, false);
	}
	
	public T find(Object primaryKey,Number revision, boolean includeDeletions) {
		return getAuditReader().find(entityClass, getEntityName(), primaryKey, revision, includeDeletions);
	}
	
	public T findRevision(Number revision) {
		return getAuditReader().findRevision(entityClass, revision);
	}
	
	public Map<Number, T> findRevisions(Set<Number> revisions) {
		return getAuditReader().findRevisions(entityClass, revisions);
	}
}
