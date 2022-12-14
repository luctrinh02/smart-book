package com.dantn.bookStore.dto.request;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.dantn.bookStore.adapter.DtoToEntity;
import com.dantn.bookStore.entities.Author;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.BookStatus;
import com.dantn.bookStore.entities.Publisher;

public class BookRequest implements DtoToEntity<Book> {
    private Integer id;
    @NotBlank(message = "Không bỏ trống tên sách")
    private String name;
    @NotBlank(message = "Không bỏ trống ISBN")
    private String isbn;
    @NotBlank(message = "Không bỏ trống số trang")
    @Positive(message = "Số trang là số nguyên lớn hơn 0")
    @Size(max = 4, message = "Số trang quá lớn")
    private String numOfPage;
    @NotBlank(message = "Vui lòng chọn tác giả")
    private String author;
    @NotBlank(message = "Vui lòng chọn nhà xuất bản")
    private String publisher;
    @NotBlank(message = "Vui lòng chọn trạng thái")
    private String status;
    @NotBlank(message = "Không bỏ trống gia bán")
    @Positive(message = "Số tiền là số nguyên lớn hơn 0")
    @Size(max = 10, message = "Số tiền quá lớn")
    private String price;
    @PositiveOrZero(message = "Giảm giá là số nguyên lớn hơn hoặc bằng 0")
    @Max(value = 100, message = "Tối đa giảm 100%")
    private String discount;
    @Positive(message = "Số lượng là số nguyên lớn hơn 0")
    @Size(max = 5, message = "Số lượng quá lớn")
    private String amount;
    private String description;
    @NotBlank(message = "Không bỏ trống thể loại")
    private String type;
//    @NotBlank(message = "Không bỏ trống nhân vật")
    private String charactor;
//    @NotBlank(message = "Không bỏ trống nội dung")
    private String content;
    private MultipartFile file;
    @Positive(message = "Chiều dài là số nguyên lớn hơn 0")
    @Size(max = 3, message = "Chiều dài quá lớn")
    private String height;
    @Positive(message = "Độ dày là số nguyên lớn hơn 0")
    @Size(max = 3, message = "Độ dày quá lớn")
    private String length;
    @Positive(message = "Chiều rộng là số nguyên lớn hơn 0")
    @Size(max = 3, message = "Chiều rộng quá lớn")
    private String width;
    @Positive(message = "Khối lượng là số nguyên lớn hơn 0")
    @Size(max = 5, message = "Khối lượng quá lớn")
    private String weight;
    @Positive(message = "Năm là số nguyên lớn hơn 0")
    @Size(min = 4,max = 4, message = "Năm không hợp lệ")
    private String year;
    private String saleTime;
    
    
    @Override
	public String toString() {
		return "BookRequest [id=" + id + ", name=" + name + ", isbn=" + isbn + ", numOfPage=" + numOfPage + ", author="
				+ author + ", publisher=" + publisher + ", status=" + status + ", price=" + price + ", discount="
				+ discount + ", amount=" + amount + ", description=" + description + ", type=" + type + ", charactor="
				+ charactor + ", content=" + content + ", file=" + file + ", height=" + height + ", length=" + length
				+ ", width=" + width + ", weight=" + weight + ", year=" + year + ", saleTime=" + saleTime + "]";
	}

	public String getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNumOfPage() {
        return numOfPage;
    }

    public void setNumOfPage(String numOfPage) {
        this.numOfPage = numOfPage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCharactor() {
        return charactor;
    }

    public void setCharactor(String charactor) {
        this.charactor = charactor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public Book changeToEntity(Book b) {
    	SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");
    	b.setId(id);
        b.setAmount(Long.parseLong(amount));
        b.setCharactor(charactor);
        b.setContent(content);
        b.setDiscount(Integer.parseInt(discount));
        b.setDescription(description);
        b.setISBN(isbn);
        b.setType(type);
        b.setName(name);
        b.setNumOfPage(Integer.parseInt(numOfPage));
        b.setPrice(new BigDecimal(price));
        b.setHeight(Integer.parseInt(height));
        b.setLength(Integer.parseInt(length));
        b.setWeight(Integer.parseInt(weight));
        b.setWidth(Integer.parseInt(width));
        b.setYear(Integer.parseInt(year));
        try {
			b.setSaleTime(fmt.parse(saleTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        if (b.getId() == null) {
            b.setCreatedTime(new Date());
            b.setEvaluate(0);
            b.setPoint(0);
            b.setSaleAmount((long) 0);
        } else {
            b.setUpdatedTime(new Date());
        }
        return b;
    }

}
