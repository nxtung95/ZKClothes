package vnua.fita.controller;

import java.util.List;
import java.util.Set;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.ext.Selectable;

import vnua.fita.bean.Clothes;
import vnua.fita.model.ClothesServiceImpl;
import vnua.fita.service.ClothesService;

public class HomeController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	@Wire
	private Label titleLabel;
	@Wire
	private Listbox clothesListBox;
	@Wire
	private Hlayout clothesDetail;
	@Wire
	private Label nameLabel;
	@Wire
	private Label brandLabel;
	@Wire
	private Label priceLabel;
	@Wire
	private Label quantityLabel;
	@Wire
	private Label originLabel;
	@Wire
	private Label sizeLabel;
	@Wire
	private Image previewImage;
	@Wire
	private A back;
	
	// đối tượng của tầng model dùng để truy vấn database
	private ClothesService clothesService = new ClothesServiceImpl("jdbc:mysql://localhost:3306/training?useSSL=false&serverTimezone=UTC",
	"root", "root");
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		String keyword = (String) Executions.getCurrent().getParameter("type");
		if(keyword != null) {
			titleLabel.setValue(keyword.toUpperCase() + " CLOTHES LIST:");
		} else { // keyword = null khi được gọi bởi trang chủ (home) trên menu
			titleLabel.setValue("CLOTHES LIST:");
		}
		
		List<Clothes> result = clothesService.search(keyword);
		clothesListBox.setModel(new ListModelList<Clothes>(result));
	}
	
	
	
	@Listen("onSelect = #clothesListBox")
	public void showDetail(){
		Set<Clothes> selection = ((Selectable<Clothes>) clothesListBox.getModel()).getSelection();
		
		if (selection!=null && !selection.isEmpty()){
			Clothes selected = selection.iterator().next();
			nameLabel.setValue(selected.getName());
			brandLabel.setValue(selected.getBrand());
			priceLabel.setValue(selected.getPrice().toString());
			quantityLabel.setValue(selected.getQuantity().toString());
			originLabel.setValue(selected.getOrigin());
			sizeLabel.setValue(selected.getSize());
			previewImage.setSrc(selected.getPreview());
		}
		titleLabel.setValue("CLOTHES DETAIL:");
		clothesListBox.setVisible(false);
		clothesDetail.setVisible(true);
	}
	
	
	@Listen("onClick = #back")
	public void backClothesList(){
		titleLabel.setValue("CLOTHES LIST:");
		clothesListBox.setVisible(true);
		clothesDetail.setVisible(false);
	}
}
