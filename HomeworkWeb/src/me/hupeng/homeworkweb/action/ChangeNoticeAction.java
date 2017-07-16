package me.hupeng.homeworkweb.action;

import me.hupeng.homeworkweb.bean.Classes;
import me.hupeng.homeworkweb.model.MessageModel;
import me.hupeng.homeworkweb.service.ClassesService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * �޸İ༶����*/
public class ChangeNoticeAction extends ActionSupport{
	private String notice;
	private String classId;
	private ClassesService classesService;
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public ClassesService getClassesService() {
		return classesService;
	}

	public void setClassesService(ClassesService classesService) {
		this.classesService = classesService;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		//��֤�û�ʲôΪ��ʦ�����Ա
		Integer classIdInteger;
		MessageModel messageModel = new MessageModel();
		SecurityAction securityAction = new SecurityAction();
		if (!securityAction.checkTeacher()) {
			messageModel.setData("ϵͳ��ʾ", "�û������֤ʧ��", "logout.action");
			return "message";
		}
		//��֤�����ĺϷ���
		if (notice == null || classId == null) {
			messageModel.setData("ϵͳ��ʾ", "�������Ϸ�", "logout.action");
			return "message";
		}
		try {
			classIdInteger = Integer.parseInt(classId);
		} catch (Exception e) {
			// TODO: handle exception
			messageModel.setData("ϵͳ��ʾ", "�������Ϸ�", "logout.action");
			return "message";
		}
		Classes classes = classesService.getClassByClassId(classIdInteger);
		if (classes == null) {
			messageModel.setData("ϵͳ��ʾ", "�������Ϸ�", "logout.action");
			return "message";
		}
		if (securityAction.getType() == 0 || classes.getUserId() == securityAction.getUserId()) {
			classesService.changeNotice(classes.getClassId(), notice);
		}
		return SUCCESS;
	}
	
}
