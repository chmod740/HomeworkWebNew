package me.hupeng.homeworkweb.action;

import org.apache.struts2.ServletActionContext;

import me.hupeng.homeworkweb.model.MessageModel;
import me.hupeng.homeworkweb.service.ClassesService;

import com.opensymphony.xwork2.ActionSupport;
/**
 * ��ʦ�û����߹���Ա�û���Ӱ༶
 * */
public class AddClassAction extends ActionSupport{
	private MessageModel messageModel;
	private String className;
	private String notice;
	private ClassesService classesService;
	public MessageModel getMessageModel() {
		return messageModel;
	}

	public void setMessageModel(MessageModel messageModel) {
		this.messageModel = messageModel;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
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
		//�ж��û���ݣ���Ӱ༶Ϊ��ʦ�˻�ӵ�е�Ȩ��
		SecurityAction securityAction = new SecurityAction();
		messageModel = new MessageModel();
		if (!(securityAction.checkAdmin() || securityAction.checkTeacher())) {
//			messageModel.setData("ϵͳ��ʾ","�û�У��ʧ��","logout.action");
//			return "message";
			ServletActionContext.getResponse().setStatus(500);
		}
		if (className == null || notice == null) {
//			messageModel.setData("ϵͳ��ʾ", "�����Ƿ�", "classInfo.action");
			ServletActionContext.getResponse().setStatus(500);
		}
		if (className.equals("")) {
//			messageModel.setData("ϵͳ��ʾ", "�༶���Ʋ���Ϊ��", "classInfo.action");
			ServletActionContext.getResponse().setStatus(500);
		}
		classesService.add(className, notice, securityAction.getUserId());
//		messageModel.setData("ϵͳ��ʾ", "�༶��ӳɹ���", "classInfo.action");
		return SUCCESS;
	}
}
