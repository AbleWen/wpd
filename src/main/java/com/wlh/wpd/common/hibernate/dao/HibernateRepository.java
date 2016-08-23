package com.wlh.wpd.common.hibernate.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.query.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.wlh.wpd.common.hibernate.criterion.CommonRestrictions;
import com.wlh.wpd.common.hibernate.util.GBKOrder;
import com.wlh.wpd.common.hibernate.util.HibernateUtil;
import com.wlh.wpd.common.hibernate.util.OrderByInfo;
import com.wlh.wpd.common.hibernate.util.QueryCondition;
import com.wlh.wpd.common.hibernate.util.QuerySpecification;
import com.wlh.wpd.common.page.PageList;
import com.wlh.wpd.common.page.Paginator;



public abstract class HibernateRepository<T extends Serializable> extends
		HibernateDaoSupport implements IDao<T> {
	
	/** 业务实体对象 */
	private Class<?> poClass;

	/** 查询记录数 */
	private int totalCount = 0;

	/**
	 * 构造函数
	 * 
	 * @param poClass
	 *            Dao类要操作的实体对象
	 * @return
	 * @throws
	 */
	public HibernateRepository(Class<?> poClass) {
		this.poClass = poClass;
	}

	/**
	 * 新增实体对象方法
	 * 
	 * @param po
	 *            要增加的业务实体对象
	 * @throws DataAccessException
	 *             数据访问异常
	 */
	public void create(T po) throws DataAccessException {
		getHibernateTemplate().save(po);
	}

	/**
	 * 修改实体对象方法
	 * 
	 * @param po
	 *            要修改的业务实体对象
	 * @return
	 * @throws DataAccessException
	 *             数据访问异常
	 */
	public void update(T po) throws DataAccessException {
		getHibernateTemplate().merge(po);
	}

	/**
	 * 删除实体对象方法
	 * 
	 * @param po
	 *            要删除的业务实体对象
	 * @return
	 * @throws DataAccessException
	 *             数据访问异常
	 */
	public void delete(T po) throws DataAccessException {
		getHibernateTemplate().delete(po);
	}

	/**
	 * 按id查询实体对象方法
	 * 
	 * @param id
	 *            要查询的id
	 * @return 该id对应的实体对象
	 * @throws DataAccessException
	 *             数据访问异常
	 */
	@SuppressWarnings("unchecked")
	public T getById(Long id) throws DataAccessException {
		return (T) getHibernateTemplate().get(poClass, id);
	}

	/**
	 * 查询所有实体对象方法, 按名称排序
	 * 
	 * @param
	 * @return 实体对象列表
	 * @throws DataAccessException
	 *             数据访问异常
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() throws DataAccessException {
		String query = "from " + poClass.getName()
				+ " obj order by obj.name asc";

		// 执行查询
		return (List<T>) getHibernateTemplate().find(query);

	}

	/**
	 * 查询所有实体对象方法, 按id排序
	 * 
	 * @param
	 * @return 实体对象列表
	 * @throws DataAccessException
	 *             数据访问异常
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAllOrderById() throws DataAccessException {
		String query = "from " + poClass.getName() + " obj order by obj.id asc";

		// 执行查询
		return (List<T>) getHibernateTemplate().find(query);

	}

	/**
	 * 带条件的分页查询,调用DAO层的findByCriteria方法查找符合条件的记录
	 * 
	 * @param map
	 *            条件参数的MAP或者是实体
	 * @param currPage
	 *            当前页数
	 * @param pageSize
	 *            每页显示记录数
	 * @return 返回PageList封装了查询条件
	 * @throws IEPGMException
	 *             当查找失败时抛出该异常
	 */
	@SuppressWarnings("unchecked")
	public PageList<Serializable> findByCriteria(Paginator paginator)
			throws Exception {
		String query = "from " + poClass.getName() + " obj order by obj.id asc";

		return (PageList<Serializable>) HibernateUtil.getQueryList(
				this.getHibernateTemplate(), paginator, query);
	}

	/**
	 * 查询所有实体对象方法, 按id排序,带分页
	 * 
	 * @param
	 * @return 实体对象列表
	 * @throws DataAccessException
	 *             数据访问异常
	 * @throws TsServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAllOrderById(Paginator paginator)
			throws DataAccessException {
		String query = "from " + poClass.getName() + " obj order by obj.id asc";

		// 执行查询
		return (List<T>) HibernateUtil.getQueryList(
				this.getHibernateTemplate(), paginator, query);

	}

	/**
	 * 获取实体对象的记录条数
	 * 
	 * @param
	 * @return 记录条数
	 */
	public int getRowCount() {
		return totalCount;
	}

	public List<T> queryByCriteria(final QuerySpecification querySpec) {
		return this.queryByCriteria(querySpec, true);
	}
	
	/**
	 * 自定义条件查询实体对象
	 * 
	 * @param querySpec
	 * @param isPage
	 *            是否需要分页
	 * @return 实体对象列表
	 * @throws DataAccessException
	 *             数据访问异常
	 */
	public List<T> queryByCriteria(final QuerySpecification querySpec, boolean isPage) {
		// 查询条件为空, 则查询全部
		if (null == querySpec) {
			List listAll = findAll();
			totalCount = listAll.size();
			return listAll;
		}

		Session session = this.currentSession();
		Criteria criteria = session.createCriteria(getPoClass());
		if (StringUtils.isNotEmpty(querySpec.getFetchModeField())) {
			criteria.setFetchMode(querySpec.getFetchModeField(),
					FetchMode.SELECT);
		}

		if (!querySpec.hasAlias()) {
			for (Iterator it = querySpec.getAliasList().entrySet().iterator(); it
					.hasNext();) {
				Map.Entry e = (Map.Entry) it.next();
				criteria.createAlias((String) e.getKey(), (String) e.getValue());
			}
		}

		// 查询条件处理
		List<QueryCondition> condList = querySpec.getCondList();
		
		
		//区域过滤
//		if(null!=areaHolderSet){
//			//查询 areaHolderSet 需至少一个非null元素
//			if(CollectionUtils.isEmpty(areaHolderSet) 
//					|| (1==areaHolderSet.size() && areaHolderSet.contains(null))){
//				AreaHolder ah = new AreaHolder();
//				ah.setId(-1);
//				areaHolderSet.add(ah);
//			}
//			
//			//查询无区域数据 && 所属区域数据
//			if(areaHolderSet.contains(null)){
//				//null元素用于标记查询无区域数据
//				areaHolderSet.remove(null);
//				criteria.add(Restrictions.or(Restrictions.isNull("areaHolder"), Restrictions.in("areaHolder", areaHolderSet)));
//			}
//			//查询所属区域数据
//			else{
//				criteria.add(Restrictions.in("areaHolder", areaHolderSet));
//			}
//		}

		for (QueryCondition cond : condList) {
			firstSwitch(cond, criteria);
		}

		if (isPage) {
			// 分页处理
			if (querySpec.isPagingQuery() && querySpec.getPageNo() >= 0
					&& querySpec.getPageSize() > 0) {
				criteria.setProjection(Projections.rowCount());

				// 查询结果集大小
				totalCount = Integer.parseInt(criteria.list().get(0).toString());
				querySpec.setTotalCount(totalCount);

				// 恢复投影引起的criteria的变化, 否则查询出的结果不能以对象形式返回
				criteria.setProjection(null);
				// criteria.setResultTransformer(new
				// RootEntityResultTransformer());
				criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

				// 设置分页属性
				int startIndex = querySpec.getPageSize()
						* (querySpec.getPageNo() - 1);
				criteria.setFirstResult(startIndex).setMaxResults(
						querySpec.getPageSize());
			}
		}

		// 排序处理
		List<OrderByInfo> OrderByInfo = querySpec.getOrderByList();
		for (OrderByInfo orderBy : OrderByInfo) {
			if (orderBy.isPyOrder()) {
				// 按拼音排序
				GBKOrder gBKOrder = new GBKOrder(orderBy.getPropertyName(),
						orderBy.isAsc());
				criteria.addOrder(gBKOrder);
			} else {
				// 默认排序
				Property property = Property.forName(orderBy.getPropertyName());
				criteria.addOrder(orderBy.isAsc() ? property.asc() : property
						.desc());
			}
		}

		List resultList = criteria.list();

		if (isPage) {
			// 带分页参数的列表
			PageList<T> pageList = new PageList<T>(querySpec.getPageNo(),
					querySpec.getPageSize(), querySpec.getTotalCount());
			pageList.addAll(resultList);

			// 返回查询结果
			return pageList;
		} else {
			return resultList;
		}		
	}
	
	
	/**
	 * 区域过滤
	 * @param areaIds
	 * @param querySpec
	 * @return
	 */
//	public List<T> queryByCriteria(Set<AreaHolder> areaHolderSet, final QuerySpecification querySpec){
//		return this.queryByCriteria(areaHolderSet, querySpec, true);
//	}

	/**
	 * 自定义条件查询实体对象
	 * 
	 * @param areaLimit
	 *            是否用户区域过滤
	 * @param querySpec
	 * @param isPage
	 *            是否需要分页
	 * @return 实体对象列表
	 * @throws DataAccessException
	 *             数据访问异常
	 */
//	@SuppressWarnings("unchecked")
//	public List<T> queryByCriteria(Set<AreaHolder> areaHolderSet, final QuerySpecification querySpec,
//			boolean isPage) {
//		// 查询条件为空, 则查询全部
//		if (null == querySpec) {
//			List listAll = findAll();
//			totalCount = listAll.size();
//			return listAll;
//		}
//
//		Session session = this.currentSession();
//		Criteria criteria = session.createCriteria(getPoClass());
//		if (StringUtils.isNotEmpty(querySpec.getFetchModeField())) {
//			criteria.setFetchMode(querySpec.getFetchModeField(),
//					FetchMode.SELECT);
//		}
//
//		if (!querySpec.hasAlias()) {
//			for (Iterator it = querySpec.getAliasList().entrySet().iterator(); it
//					.hasNext();) {
//				Map.Entry e = (Map.Entry) it.next();
//				criteria.createAlias((String) e.getKey(), (String) e.getValue());
//			}
//		}
//
//		// 查询条件处理
//		List<QueryCondition> condList = querySpec.getCondList();
//		
//		
//		//区域过滤
//		if(null!=areaHolderSet){
//			//查询 areaHolderSet 需至少一个非null元素
//			if(CollectionUtils.isEmpty(areaHolderSet) 
//					|| (1==areaHolderSet.size() && areaHolderSet.contains(null))){
//				AreaHolder ah = new AreaHolder();
//				ah.setId(-1);
//				areaHolderSet.add(ah);
//			}
//			
//			//查询无区域数据 && 所属区域数据
//			if(areaHolderSet.contains(null)){
//				//null元素用于标记查询无区域数据
//				areaHolderSet.remove(null);
//				criteria.add(Restrictions.or(Restrictions.isNull("areaHolder"), Restrictions.in("areaHolder", areaHolderSet)));
//			}
//			//查询所属区域数据
//			else{
//				criteria.add(Restrictions.in("areaHolder", areaHolderSet));
//			}
//		}
//
//		for (QueryCondition cond : condList) {
//			firstSwitch(cond, criteria);
//		}
//
//		if (isPage) {
//			// 分页处理
//			if (querySpec.isPagingQuery() && querySpec.getPageNo() >= 0
//					&& querySpec.getPageSize() > 0) {
//				criteria.setProjection(Projections.rowCount());
//
//				// 查询结果集大小
//				totalCount = Integer.parseInt(criteria.list().get(0).toString());
//				querySpec.setTotalCount(totalCount);
//
//				// 恢复投影引起的criteria的变化, 否则查询出的结果不能以对象形式返回
//				criteria.setProjection(null);
//				// criteria.setResultTransformer(new
//				// RootEntityResultTransformer());
//				criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//
//				// 设置分页属性
//				int startIndex = querySpec.getPageSize()
//						* (querySpec.getPageNo() - 1);
//				criteria.setFirstResult(startIndex).setMaxResults(
//						querySpec.getPageSize());
//			}
//		}
//
//		// 排序处理
//		List<OrderByInfo> OrderByInfo = querySpec.getOrderByList();
//		for (OrderByInfo orderBy : OrderByInfo) {
//			if (orderBy.isPyOrder()) {
//				// 按拼音排序
//				GBKOrder gBKOrder = new GBKOrder(orderBy.getPropertyName(),
//						orderBy.isAsc());
//				criteria.addOrder(gBKOrder);
//			} else {
//				// 默认排序
//				Property property = Property.forName(orderBy.getPropertyName());
//				criteria.addOrder(orderBy.isAsc() ? property.asc() : property
//						.desc());
//			}
//		}
//
//		List resultList = criteria.list();
//
//		if (isPage) {
//			// 带分页参数的列表
//			PageList<T> pageList = new PageList<T>(querySpec.getPageNo(),
//					querySpec.getPageSize(), querySpec.getTotalCount());
//			pageList.addAll(resultList);
//
//			// 返回查询结果
//			return pageList;
//		} else {
//			return resultList;
//		}
//	}
	
	/**
	 * <为了修改findBugs查出的bug改成这样> <功能详细描述>
	 * 
	 * @param cond
	 * @param criteria
	 *            [参数说明]
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	private void firstSwitch(QueryCondition cond, Criteria criteria) {
		switch (cond.getOperator()) {
		// 等于
		case EQ:
			criteria.add(Restrictions.eq(cond.getProperty(), cond.getValue()));
			break;

		// 大小写不敏感的等于
		case IEQ:
			SimpleExpression criterion = (SimpleExpression) Restrictions.eq(
					cond.getProperty(), cond.getValue());
			criterion.ignoreCase();
			criteria.add(criterion);
			break;

		// 大小写不敏感的任意匹配
		case ILIKE:
			criteria.add(CommonRestrictions.ilike(
					cond.getProperty(),
					((String) cond.getValue()).toLowerCase(Locale.getDefault()),
					MatchMode.ANYWHERE));
			break;

		// 大小写不敏感的前匹配
		case ISLIKE:
			criteria.add(Restrictions.ilike(
					cond.getProperty(),
					((String) cond.getValue()).toLowerCase(Locale.getDefault()),
					MatchMode.START));
			break;

		// 大小写不敏感的后匹配
		case IELIKE:
			criteria.add(Restrictions.ilike(
					cond.getProperty(),
					((String) cond.getValue()).toLowerCase(Locale.getDefault()),
					MatchMode.END));
			break;
		default:
			secondSwitch(cond, criteria);
		}
	}

	/**
	 * <为了修改findBugs查出的bug改成这样> <功能详细描述>
	 * 
	 * @param cond
	 * @param criteria
	 *            [参数说明]
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	private void secondSwitch(QueryCondition cond, Criteria criteria) {
		switch (cond.getOperator()) {
		// 大小写敏感的精确匹配
		case IEXACTLIKE:
			criteria.add(Restrictions.like(
					cond.getProperty(),
					((String) cond.getValue()).toLowerCase(Locale.getDefault()),
					MatchMode.EXACT));
			break;

		// 大小写敏感的任意匹配
		case LIKE:
			criteria.add(CommonRestrictions.like(cond.getProperty(),
					(String) cond.getValue(), MatchMode.ANYWHERE));
			break;

		// 大小写敏感的前匹配
		case SLIKE:
			criteria.add(Restrictions.like(cond.getProperty(),
					(String) cond.getValue(), MatchMode.START));
			break;

		// 大小写敏感的后匹配
		case ELIKE:
			criteria.add(Restrictions.like(cond.getProperty(),
					(String) cond.getValue(), MatchMode.END));
			break;

		// 大小写敏感的精确匹配
		case EXACTLIKE:
			criteria.add(Restrictions.like(cond.getProperty(),
					(String) cond.getValue(), MatchMode.EXACT));
			break;
		default:
			thirdSwitch(cond, criteria);
		}
	}

	/**
	 * <为了修改findBugs查出的bug改成这样> <功能详细描述>
	 * 
	 * @param cond
	 * @param criteria
	 *            [参数说明]
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	private void thirdSwitch(QueryCondition cond, Criteria criteria) {
		switch (cond.getOperator()) {
		// between
		case BETWEEN:
			Object[] objs = (Object[]) cond.getValue();
			criteria.add(Restrictions.between(cond.getProperty(), objs[0],
					objs[1]));
			break;

		case IN:
			// 目前对in的使用, 局限在以id为查询条件, 此处没有考虑其他情况
			// 如果查询条件本身是id, 则直接使用,
			if (cond.getProperty().equals("id") || cond.getProperty().equals("name") || cond.getProperty().equals("m.resourceCode")) {
				criteria.add(Restrictions.in(cond.getProperty(), (Object[]) cond.getValue()));
			}
			// 如果查询条件是对象名, 则添加".id"
			else {
//				criteria.createAlias(cond.getProperty(), cond.getProperty());
				criteria.add(Restrictions.in(cond.getProperty() + ".id", (Object[]) cond.getValue()));
			}
			break;

		// 大于
		case GT:
			criteria.add(Restrictions.gt(cond.getProperty(), cond.getValue()));
			break;

		// 小于
		case LT:
			criteria.add(Restrictions.lt(cond.getProperty(), cond.getValue()));
			break;

		// 大于等于
		case GE:
			criteria.add(Restrictions.ge(cond.getProperty(), cond.getValue()));
			break;
		default:
			fourthSwitch(cond, criteria);
		}
	}

	/**
	 * <为了修改findBugs查出的bug改成这样> <功能详细描述>
	 * 
	 * @param cond
	 * @param criteria
	 *            [参数说明]
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	@SuppressWarnings("unchecked")
	private void fourthSwitch(QueryCondition cond, Criteria criteria) {
		switch (cond.getOperator()) {
		// 小于等于
		case LE:
			criteria.add(Restrictions.le(cond.getProperty(), cond.getValue()));
			break;

		// 不等于
		case NE:
			criteria.add(Restrictions.ne(cond.getProperty(), cond.getValue()));
			break;

		// 或者等于
		case OR_EQ:
			List<Object> typeList = (List<Object>) cond.getValue();
			// 必须确保size大于1才能拼接查询
			int size = typeList.size();
			if (size > 1) {
				LogicalExpression logicExp = Restrictions.or(
						Restrictions.eq(cond.getProperty(), typeList.get(0)),
						Restrictions.eq(cond.getProperty(), typeList.get(1)));

				for (int i = 2; i < size; i++) {
					logicExp = Restrictions.or(logicExp, Restrictions.eq(
							cond.getProperty(), typeList.get(i)));
				}
				criteria.add(logicExp);
			}
			break;
		// 或者in
		case OR_IN:
			List<Object> tList = (List<Object>) cond.getValue();
			// 必须确保size大于1才能拼接查询
			int count = tList.size();
			if (count > 1) {
				LogicalExpression logicExp = Restrictions.or(
						Restrictions.in(cond.getProperty(),
								(Object[]) tList.get(0)),
						Restrictions.in(cond.getProperty(),
								(Object[]) tList.get(1)));

				for (int i = 2; i < count; i++) {
					logicExp = Restrictions.or(logicExp, Restrictions.in(
							cond.getProperty(), (Object[]) tList.get(i)));
				}
				criteria.add(logicExp);
			}
			break;
		default:
			fifthSwitch(cond, criteria);
		}
	}
	
	/**
	 * <为了修改findBugs查出的bug改成这样> <功能详细描述>
	 * 
	 * @param cond
	 * @param criteria
	 *            [参数说明]
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	private void fifthSwitch(QueryCondition cond, Criteria criteria) {
		switch (cond.getOperator()) {
		// 大于等于 或 空
		case GE_OR_NUll:
			criteria.add(Restrictions.or(Restrictions.ge(cond.getProperty(), cond.getValue()), Restrictions.isNull(cond.getProperty())));
			break;

		// 小于等于 或 空
		case LE_OR_NUll:
			criteria.add(Restrictions.or(Restrictions.le(cond.getProperty(), cond.getValue()), Restrictions.isNull(cond.getProperty())));
			break;
		default:
			break;
		}
	}

	/**
	 * 按name查询实体对象
	 * 
	 * @param name
	 *            要查询的实体名称
	 * @return 实体对象列表
	 * @throws DataAccessException
	 *             数据访问异常
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByName(String name) throws DataAccessException {
		// 构造查询语句
		String query = "from " + poClass.getName() + " obj where obj.name = ?";

		// 执行查询
		return (List<T>) getHibernateTemplate().find(query, name);
	}

	/**
	 * 按name查询实体对象 提供分页使用
	 * 
	 * @param name
	 *            要查询的实体名称
	 * @return 实体对象列表
	 * @throws DataAccessException
	 *             数据访问异常
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByName(Paginator paginator, String name)
			throws DataAccessException {
		// 构造查询语句
		String query = "from " + poClass.getName() + " obj where obj.name = ?";

		// 执行查询
		return (List<T>) HibernateUtil.getQueryListByValue(
				this.getHibernateTemplate(), paginator, query,
				new String[] { name });
	}

	/**
	 * 批量删除方法
	 * 
	 * @param ids
	 * @param clazz
	 *            [参数说明]
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	public void delbatch(Long[] ids, Class<T> clazz) {
		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];
			Object obj = getHibernateTemplate().load(clazz, id);
			getHibernateTemplate().delete(obj);
		}
	}

	/**
	 * 属性的get方法
	 * 
	 * @return 业务实体对象
	 */
	protected Class<?> getPoClass() {
		return poClass;
	}

	/**
	 * 执行HQL语句
	 * 
	 * @param hql
	 * @return [参数说明]
	 * @return int [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	public int executeUpdate(String hql) {
		
		Query queryupdate = this.currentSession().createQuery(hql);
		return queryupdate.executeUpdate();
	}
	
	/**
	 * 批量保存、更新方法
	 * @param session 
	 * @param objects
	 * @throws Exception 
	 */
	public void saveBatch(Session session, List<T> objs) throws Exception{
		Transaction tx = null ;
		try{
			tx = session.beginTransaction();
			for(int i=0;i<objs.size();i++){
				T obj = objs.get(i);
				session.saveOrUpdate(obj);
				if(i%50==0){
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			throw e;
		}
	}
}
