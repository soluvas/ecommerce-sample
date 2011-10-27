package com.soluvas.sample.ecommerce.view;
import java.util.List;
import java.util.ArrayList;
   
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.soluvas.sample.ecommerce.domain.Product;
import org.metawidget.forge.navigation.MenuItem;
import org.metawidget.forge.persistence.PaginationHelper;
import org.metawidget.forge.persistence.PersistenceUtil;
import org.jboss.seam.transaction.Transactional;

@Transactional @Named
@RequestScoped
public class ProductBean extends PersistenceUtil implements MenuItem
{
   private static final long serialVersionUID = 1L;
   
   private List<Product> list = null;
   private Product product = new Product();
   private long id = 0;
   private PaginationHelper<Product> pagination;		
   
   public Class<?> getItemType()
   {
      return Product.class;
   }
   
   public String getLiteralPath()
   {
      return null;
   }

   public String getLabel()
   {
      return null;
   }

   public void load()
   {
      product = findById(Product.class, id);
   }
   
   public String create()
   {
      create(product);
      return "view?faces-redirect=true&id=" + product.getId();
   }

   public String delete()
   {
      delete(product);
      return "list?faces-redirect=true";
   }

   public String save()
   {
      save(product);
      return "view?faces-redirect=true&id=" + product.getId();
   }

   public long getId()
   {
      return id;
   }

   public void setId(long id)
   {
      this.id = id;
      if(id>0){
			load();
	  }	
   }
   
   public Product getProduct()
   {
      return product;
   }

   public void setProduct(Product product)
   {
      this.product = product;
   }

   public List<Product> getList()
   {
      if(list == null)
      {
         list = getPagination().createPageDataModel();
      }
      return list;
   }

   public void setList(List<Product> list)
   {
      this.list = list;
   }
  
   public PaginationHelper<Product> getPagination() 
   {
		if (pagination == null) 
		{
			pagination = new PaginationHelper<Product>(10) 
			{
				@Override
				public int getItemsCount() {
					return count(Product.class);
				}

				@Override
				public List<Product> createPageDataModel() 
				{
					return new ArrayList<Product>(findAll(Product.class,
							 getPageFirstItem(), getPageSize() ));
				}
			};
		}
		return pagination;
	}

   public void setPagination(final PaginationHelper<Product> helper)
   {
      pagination = helper;
   }
}