package ee.ut.math.tvt;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemTableModel;

import java.util.NoSuchElementException;

public class SalesSystemTableModelTest {

	private class DisplayableItemImpl implements DisplayableItem {

		private long id;

		public DisplayableItemImpl(long id) {
			super();
			this.id = id;
		}

		@Override
		public Long getId() {
			return id;
		}
	}

	private class SalesSystemTableModelImpl extends
			SalesSystemTableModel<DisplayableItem> {

		public SalesSystemTableModelImpl(String[] headers) {
			super(headers);
			
		}
		private static final long serialVersionUID = 1L;

		@Override
		protected Object getColumnValue(DisplayableItem item, int columnIndex) {
			return item.getId();
		}
	}

	DisplayableItem item1;
	DisplayableItem item2;
	DisplayableItem item3;
	ArrayList<DisplayableItem> lst;
	private SalesSystemTableModelImpl sstm;

	@Before
	public void setUp() {
		item1 = new DisplayableItemImpl(1);
		item2 = new DisplayableItemImpl(2);
		sstm = new SalesSystemTableModelImpl(new String[] {"A", "B", "C", "D"});
		lst = new ArrayList<>();
		lst.add(item1);
		lst.add(item2);
		lst.add(item3);
		sstm.populateWithData(lst);
	}

	@Test
	public void testGetColumnCount() {
		assertEquals(4, sstm.getColumnCount());
	}

	@Test
	public void testGetColumnName() {
		assertEquals("D", sstm.getColumnName(3));
	}

	@Test
	public void testGetRowCount() {
		assertEquals(3, sstm.getRowCount());
	}

	@Test
	public void testGetValueAt() {
		assertEquals(item1.getId(), sstm.getValueAt(0, 0));
	}

	@Test
	public void testGetItemById() {
		assertEquals(item1, sstm.getItemById(item1.getId()));
	}

	@Test
	public void testGetTableRows() {
		assertEquals(lst, sstm.getTableRows());
	}

	@Test(expected = NoSuchElementException.class)
	public void testClear() {
		SalesSystemTableModelImpl sstm = new SalesSystemTableModelImpl(null);
		sstm.populateWithData(lst);

		sstm.clear();
		sstm.getItemById(item1.getId());
	}
}