package me.hupeng.homeworkweb.action;

import java.io.FileInputStream;
import java.io.InputStream;

import me.hupeng.homeworkweb.bean.Courseware;
import me.hupeng.homeworkweb.bean.Task;
import me.hupeng.homeworkweb.model.MessageModel;
import me.hupeng.homeworkweb.service.CoursewareService;
import me.hupeng.homeworkweb.service.HomeworkService;
import me.hupeng.homeworkweb.service.TaskService;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class DownloadCoursewareAction extends ActionSupport{
	private String filename;
	private MessageModel messageModel;
	private String cid;
	private CoursewareService coursewareService;
	private TaskService taskService;
	private Integer coursewareId;
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public MessageModel getMessageModel() {
		return messageModel;
	}

	public void setMessageModel(MessageModel messageModel) {
		this.messageModel = messageModel;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public CoursewareService getCoursewareService() {
		return coursewareService;
	}

	public void setCoursewareService(CoursewareService coursewareService) {
		this.coursewareService = coursewareService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public InputStream getTargetFile() throws Exception{
		String string  = coursewareService.getByCoursewareId(coursewareId).getPath();
		filename = coursewareService.getByCoursewareId(coursewareId).getFilename();
		return new FileInputStream(string);

	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		messageModel = new MessageModel();
		SecurityAction securityAction = new SecurityAction();
		if (!securityAction.checkUser()) {
			return "login";
		}
		//��֤�û��Ƿ��������ļ���Ȩ��
		
		try {
			System.out.println(cid);
			coursewareId = Integer.parseInt(cid);
		} catch (Exception e) {
			// TODO: handle exception
			messageModel.setData("ϵͳ��ʾ", "�����Ƿ������ip��" + ServletActionContext.getRequest().getRemoteAddr() +"�ѱ�ϵͳ��¼��", "classInfo.action");
			return "message";
		}
		
		Courseware courseware = coursewareService.getByCoursewareId(coursewareId);
		if (courseware == null) {
			messageModel.setData("ϵͳ��ʾ", "����Ȩ���ش��ļ������ip��" + ServletActionContext.getRequest().getRemoteAddr() +"�ѱ�ϵͳ��¼��", "classInfo.action");
//			return "message";
		}
//		Task task = null;
//		try {
//			task = taskService.getTaskById(homeworkService.getHomeworkById(homeworkIdInteger).getTaskId());
//		} catch (Exception e) {
//			// TODO: handle exception
//			messageModel.setData("ϵͳ��ʾ", "����Ȩ���ش��ļ������ip��" + ServletActionContext.getRequest().getRemoteAddr() +"�ѱ�ϵͳ��¼��", "classInfo.action");
//			return "message";
//		}
		
		if (securityAction.getType() == 0) {
			return SUCCESS;
		}
//		if (task == null || !task.getUserId().equals(securityAction.getUserId())) {
//			messageModel.setData("ϵͳ��ʾ", "����Ȩ���ش��ļ������ip��" + ServletActionContext.getRequest().getRemoteAddr() +"�ѱ�ϵͳ��¼��", "classInfo.action");
//			return "message";
//		}
		return SUCCESS;
	}
	
}