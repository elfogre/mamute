package br.com.caelum.brutal.dao;

import java.util.List;

import org.hibernate.Session;

import br.com.caelum.brutal.model.User;

public class WithUserDAO<T> {

	private static final Integer PAGE_SIZE = 5;
	private final Session session;
	private final Class<T> clazz;
	private final String role;

	public WithUserDAO(Session session, Class<T> clazz, UserRole role) {
		this.session = session;
		this.clazz = clazz;
		this.role = role.getRole();
	}

	public List<T> by(User user, OrderType orderByWhat, Integer page) {
		List<T> items = withUserBy(user, orderByWhat.getOrder(), page);
		return items;		
	}
	
	@SuppressWarnings("unchecked")
	private List<T> withUserBy(User user, String order, Integer page) {
		List<T> items = session.createQuery("select p from "+ clazz.getSimpleName() +" as p join p."+ role +" r where r = :user " + order)
				.setParameter("user", user)
				.setMaxResults(PAGE_SIZE)
				.setFirstResult(PAGE_SIZE * (page-1))
				.list();
		return items;
	}
	
	public Long count(User user) {
		return (Long) session.createQuery("select count(p) from "+ clazz.getSimpleName() +" as p join p."+ role +" r where r = :user ")
						.setParameter("user", user)
						.uniqueResult();
	}
	
	public long numberOfPagesTo(User user) {
		Long count = count(user);
		return calculatePages(count);
	}
	
	private long calculatePages(Long count) {
		long result = count/PAGE_SIZE.longValue();
		if (count % PAGE_SIZE.longValue() != 0) {
			result++;
		}
		return result;
	}
	
	public static enum OrderType {
		ByDate {
			@Override
			public String getOrder() {
				return "order by p.createdAt desc";
			}
		}, ByVotes() {
			@Override
			public String getOrder() {
				return "order by p.voteCount desc";
			}
		};

		public abstract String getOrder();
	}
	
	public static enum UserRole {
		AUTHOR {
			@Override
			public String getRole() {
				return "author";
			}
		},
		WATCHER {
			@Override
			public String getRole() {
				return "watcher";
			}
		};
		
		public abstract String getRole();
	}
	
}