package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.FollowUpModel;
import com.rays.pro4.Model.RoleModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

//TODO: Auto-generated Javadoc
/**
 * The Class FollowUpCtl.
 * 
 * @author Madhumita Rajarwal
 * 
 */
@WebServlet(urlPatterns = {"/ctl/FollowUpCtl"})
public class FollowUpCtl extends BaseCtl{

	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(FollowUpCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		System.out.println("uctl preload");
		RoleModel model = new RoleModel();
		try {
			List l = model.list();
			request.setAttribute("roleList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");
		log.debug("FollowUpCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("patient"))) {
			request.setAttribute("patient", PropertyReader.getValue("error.require", "patient"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("patient"))) {
			request.setAttribute("patient", "patient name contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("doctor"))) {
			request.setAttribute("doctor", PropertyReader.getValue("error.require", "doctor"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("doctor"))) {
			request.setAttribute("doctor", " doctor contains alphabet only");
			pass = false;
		}
		

		if (DataValidator.isNull(request.getParameter("fees"))) {
			request.setAttribute("fees", PropertyReader.getValue("error.require", "fees"));
			pass = false;
		} 


		if (DataValidator.isNull(request.getParameter("visitDate"))) {
			request.setAttribute("visitDate", PropertyReader.getValue("error.require", "visitDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("visitDate"))) {
			request.setAttribute("visitDate", PropertyReader.getValue("error.date", "visitDate"));
			pass = false;
		}

		log.debug("FollowUpCtl Method validate Ended");

		return pass;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */

	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println(" uctl Base bean P bean");
		log.debug("FollowUpCtl Method populatebean Started");

		FollowUpBean bean = new FollowUpBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setPatient(DataUtility.getString(request.getParameter("patient")));

		bean.setDoctor(DataUtility.getString(request.getParameter("doctor")));

		bean.setFees(DataUtility.getLong(request.getParameter("fees")));

		bean.setVisitDate(DataUtility.getDate(request.getParameter("visitDate")));
		//System.out.println("visitDate" + bean.getvisitDate());

		populateDTO(bean, request);

		log.debug("FollowUpCtl Method populatebean Ended");

		return bean;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("FollowUpCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		FollowUpModel model = new FollowUpModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("User Edit Id >= " + id);
		if (id != 0 && id > 0) {
			System.out.println("in id > 0  condition");
			FollowUpBean bean;
			try {
				bean = model.findByPK(id);
				System.out.println(bean);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
System.out.println("huhuh");
		ServletUtility.forward(getView(), request, response);
		log.debug("FollowUpCtl Method doGet Ended");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("uctl Do Post");

		log.debug("FollowUpCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><<><>**********" + id + op);

		FollowUpModel model = new FollowUpModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			FollowUpBean bean = (FollowUpBean) populateBean(request);
			System.out.println(" U ctl DoPost 11111111");

			try {
				if (id > 0) {

					// System.out.println("hi i am in dopost update");
					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" U ctl DoPost 222222");
					ServletUtility.setSuccessMessage(" FollowUp is successfully Updated", request);

				} else {
					System.out.println(" U ctl DoPost 33333");
					long pk = model.add(bean);
					 bean.setId(pk);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("FollowUp is successfully Added", request);
					bean.setId(pk);
				}
				/*
				 * ServletUtility.setBean(bean, request);
				 * ServletUtility.setSuccessMessage("User is successfully saved", request);
				 */

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" U ctl D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" U ctl D p 5555555");

			FollowUpBean bean = (FollowUpBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" u ctl D Post  6666666");
				ServletUtility.redirect(ORSView.FOLLOWUP_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.FOLLOWUP_LIST_CTL, request, response);
			return;
			
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("FollowUpCtl Method doPostEnded");
	}

	/*
	 * (non-Javadoc) ontroller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.FOLLOWUP_VIEW;
	}

}
