package com.activedevsolutions.cloud.templateservice.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.activedevsolutions.cloud.core.dao.AbstractDao;
import com.activedevsolutions.cloud.templateservice.model.Item;

public class InMemoryDao extends AbstractDao<Item> {
	private AtomicInteger counter = new AtomicInteger(0);
	
	// Our in-memory list keyed by the id (
	private Map<Integer, Item> items = new ConcurrentHashMap<>();

	@Override
	protected int createQuery(Item item) {
		int count = counter.incrementAndGet();
		items.put(count, item);
		return count;
	}

	@Override
	protected int updateQuery(Item item) {
		Item previous = items.putIfAbsent(item.getId(), item);
		if (previous == null) {
			return 0;
		}
		else {
			return 1;
		}
	}

	@Override
	protected int deleteQuery(int id) {
		if (items.containsKey(id)) {
			items.remove(id);
			return 1;
		}
		else {
			return 0;
		}
	}
	
	@Override
	public Item getItem(int id) {
		return items.get(id);
	}
	
	@Override
	public List<Item> getList() {
		List<Item> result = new ArrayList<>();
		result.addAll(items.values());
		return result;		
	}
	
	@Override
	protected List<Map<String, Object>> getListQuery() {
		return null;
	}

	@Override
	protected List<Map<String, Object>> getItemQuery(int id) {
		return null;
	}

	@Override
	protected Item assembleItem(Map<String, Object> row) {
		return null;
	}

	@Override
	public int addChildren(int id, List<?> children) {
		return 0;
	}

	@Override
	public int deleteChildren(int id) {
		return 0;
	}
}
