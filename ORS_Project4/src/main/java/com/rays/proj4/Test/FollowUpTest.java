package com.rays.proj4.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.FollowUpModel;


/**
 * User Model Test classes.
 * 
 * @author Madhumita Rajarwal
 *
 */
public class FollowUpTest {

	public static void main(String[] args) throws Exception {
		 //testInsert();
		 //testDelete();
		 //testFindByPk();
		testUpdate();
		//testSearch();
	}


	public static void testInsert() {
		try {
			FollowUpBean bean = new FollowUpBean();

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			
			//bean.setId(1);
			bean.setPatient("minal fiske");
			bean.setDoctor("Anil Dashore");
			bean.setVisitDate(sdf.parse("12-10-1997"));
			bean.setFees(500l);

			FollowUpModel model = new FollowUpModel();

			long pk = model.add(bean);
			
			System.out.println("record insert"+pk);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private static void testUpdate() throws DuplicateRecordException, Exception {
		try {
			FollowUpBean bean = new FollowUpBean();
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			
			
			FollowUpModel model = new FollowUpModel();
			//bean = model.findByPK(5L);
			bean.setPatient("sagar agarwal");
			bean.setDoctor(" Dr.goyal ");
			bean.setVisitDate(sdf.parse("12-1-1997"));
			bean.setFees(550l);
			bean.setId(4);
			model.update(bean);

			System.out.println("test update succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}
	private static void testFindByPk() {
		try {
			FollowUpBean bean = new FollowUpBean();
			long pk = 5L;
			FollowUpModel model = new FollowUpModel();
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test find by pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getPatient());
			System.out.println(bean.getDoctor());
			System.out.println(bean.getVisitDate());
			System.out.println(bean.getFees());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}
	public static void testDelete() throws ApplicationException {
		FollowUpBean bean = new FollowUpBean();		
		bean.setId(5l);
		FollowUpModel model = new FollowUpModel();
		model.delete(bean);

	}
	private static void testSearch() {
		try {
			FollowUpBean bean = new FollowUpBean();
			FollowUpModel model = new FollowUpModel();
			List list = new ArrayList();
			//bean.setPatient("a");
			 //bean.setDoctor("anil");
			 //bean.setId(4L);
			//bean.setFees(500l);
			
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (FollowUpBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getPatient());
				System.out.println(bean.getDoctor());
				System.out.println(bean.getVisitDate());
				System.out.println(bean.getFees());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}


}

