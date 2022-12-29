package vnua.fita.service;

import java.util.List;

import vnua.fita.bean.Clothes;

public interface ClothesService {
	
	public List<Clothes> search(String keyword);
	
	public boolean insert(Clothes clothes);
	
	public boolean update(Clothes clothes);
	
	public boolean delete(int clothesId);
	
	public Clothes select(int clothesId);
}
