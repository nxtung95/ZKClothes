package vnua.fita.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Selectable;

import vnua.fita.bean.Clothes;
import vnua.fita.model.ClothesServiceImpl;
import vnua.fita.service.ClothesService;

public class SearchController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox keywordBox;
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
	
	// đối tượng của tầng model dùng để truy vấn database
	private ClothesService clothesService = new ClothesServiceImpl("jdbc:mysql://localhost:3306/training?useSSL=false&serverTimezone=UTC",
	"root", "root");
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		List<Clothes> result = clothesService.search(null);
		clothesListBox.setModel(new ListModelList<Clothes>(result));
	}
	
	@Listen("onClick = #addButton")
	 public void showAddModal() {
		 Window window = (Window)Executions.createComponents("addClothes.zul", null, null);
		 window.doModal();
	 }
	
	@Listen("onEdit=#clothesListBox")
	public void showUpdateModalDialog(ForwardEvent evt) {
		Button editBtn = (Button) evt.getOrigin().getTarget();
		String editBtnId = editBtn.getId();
		Integer clothesId = Integer.valueOf(editBtnId.substring(4));
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("clothesId", clothesId);
		Window window = (Window)Executions.createComponents("editClothes.zul", null, map);
		window.doModal();
	}
	
	// Xử lý sự kiện click nút Delete, hiện hộp thoại confirm và xử lý
	@Listen("onDelete=#clothesListBox")
	public void doDelete(ForwardEvent evt) {
		Messagebox.show("Are you sure to delete?", "Delete?", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
		 @Override
		 public void onEvent(final Event confirmEvt) throws InterruptedException {
			if (Messagebox.ON_YES.equals(confirmEvt.getName())) {
				Button deleteBtn = (Button)evt.getOrigin().getTarget();
				String deleteBtnId = deleteBtn.getId();
				Integer clothesId = Integer.valueOf(deleteBtnId.substring(6));
			
				boolean result = clothesService.delete(clothesId);
				if (!result) {
					Messagebox.show("Delete fail!");
					return;
				}
				Messagebox.show("Delete success!");
				search();
			} else {
				 return;
			 }
		 }
	 });
	}
	
	@Listen("onClick = #searchButton")
	public void search(){
		String keyword = keywordBox.getValue();
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
//		clothesListBox.setVisible(false);
//		clothesDetail.setVisible(true);
	}
}
