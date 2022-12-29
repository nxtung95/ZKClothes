package vnua.fita.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import vnua.fita.bean.Clothes;
import vnua.fita.model.ClothesServiceImpl;
import vnua.fita.service.ClothesService;

public class UpdateClothesController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	// đối tượng của tầng model dùng để truy vấn database
	private ClothesService clothesService = new ClothesServiceImpl("jdbc:mysql://localhost:3306/training?useSSL=false&serverTimezone=UTC",
	"root", "root");
	
    @Wire
    Window modalDialog;
	@Wire("#nameBox")
	private Textbox nameBox;
	@Wire("#brandBox")
	private Textbox brandBox;
	@Wire("#priceBox")
	private Textbox priceBox;
	@Wire("#quantityBox")
	private Spinner quantityBox;
	@Wire("#originBox")
	private Textbox originBox;
	@Wire("#sizeBox")
	private Textbox sizeBox;
	@Wire("#previewImage")
	private Image previewImage;
	
	private Integer clothesId;
	   
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		 super.doAfterCompose(comp);
		 Integer clothesId = (Integer)Executions.getCurrent().getArg().get("clothesId");
		 this.clothesId = clothesId;
	
		 Clothes clothes = clothesService.select(clothesId);
		 
		 nameBox.setValue(clothes.getName());
		 brandBox.setValue(clothes.getBrand());
		 priceBox.setValue(String.valueOf(clothes.getPrice()));
		 quantityBox.setValue(clothes.getQuantity());
		 originBox.setValue(clothes.getOrigin());
		 sizeBox.setValue(clothes.getSize());
		 previewImage.setSrc(clothes.getPreview());
	}
    
   @Listen("onClick = #updateClothesBtn")
   public void updateClothes() {
    // Thêm ảnh vào thư mục img
   	saveImage();
   
   	// Gọi phương thức của lớp tầng model để insert các trường dữ liệu vào database
   	Clothes clothes = new Clothes(clothesId, nameBox.getValue(), Integer.parseInt(priceBox.getValue()), 
   			brandBox.getValue(), quantityBox.getValue(), originBox.getValue(), sizeBox.getValue(), "/img/" + previewImage.getContent().getName());
   	System.out.println(clothes.toString());
   	
   	boolean result = clothesService.update(clothes);
   	if (!result) {
   		Messagebox.show("Update fail!");
   		return;
   	}
   	Messagebox.show("Update successfull!");
   	modalDialog.detach();
   	Executions.sendRedirect("searchMvc.zul"); 
   }
   
   private void saveImage() {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		String imgDir = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/img/");
		try {
			InputStream fin = previewImage.getContent().getStreamData();			
			in = new BufferedInputStream(fin);
			
			File file = new File(imgDir + previewImage.getContent().getName());
			
			OutputStream fout = new FileOutputStream(file);
			out = new BufferedOutputStream(fout);
			byte buffer[] = new byte[1024];
			int ch = in.read(buffer);
			while (ch != -1) {
				out.write(buffer, 0, ch);
				ch = in.read(buffer);
			}			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (out != null) 
					out.close();	
				
				if (in != null)
					in.close();
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}
}
