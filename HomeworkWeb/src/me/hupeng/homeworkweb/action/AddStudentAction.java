package me.hupeng.homeworkweb.action;


import me.hupeng.homeworkweb.bean.Classes;
import me.hupeng.homeworkweb.model.MessageModel;
import me.hupeng.homeworkweb.service.ChoiceclassService;
import me.hupeng.homeworkweb.service.ClassesService;
import me.hupeng.homeworkweb.service.StudentService;
import me.hupeng.homeworkweb.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class AddStudentAction extends ActionSupport{
	private String classId;
	private int cId;
	private ClassesService classesService;
	private MessageModel messageModel;
	private String stuNum;
	private String stuName;
	private StudentService studentService;
	private UserService userService;
	private ChoiceclassService choiceclassService;
	public String getClassId() {
		return classId;
	}


	public void setClassId(String classId) {
		this.classId = classId;
	}


	public MessageModel getMessageModel() {
		return messageModel;
	}


	public void setMessageModel(MessageModel messageModel) {
		this.messageModel = messageModel;
	}


	public ClassesService getClassesService() {
		return classesService;
	}


	public void setClassesService(ClassesService classesService) {
		this.classesService = classesService;
	}
	public String getStuNum() {
		return stuNum;
	}
	public void setStuNum(String stuNum) {
		this.stuNum = stuNum;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public StudentService getStudentService() {
		return studentService;
	}


	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public ChoiceclassService getChoiceclassService() {
		return choiceclassService;
	}


	public void setChoiceclassService(ChoiceclassService choiceclassService) {
		this.choiceclassService = choiceclassService;
	}


	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		SecurityAction securityAction = new SecurityAction();
		messageModel = new MessageModel();
		if (!securityAction.checkTeacher()){
			return "login";
		}
		try {
			cId = Integer.parseInt(classId);
		} catch (Exception e) {
			// TODO: handle exception
			messageModel.setData("ϵͳ��ʾ", "�����Ƿ�", "classManager.action");
			return "message";
		}
		Classes classes = classesService.getClassByClassId(cId);
		if (securityAction.getType() != 0 && !classes.getUserId().equals(securityAction.getUserId())) {
			messageModel.setData("ϵͳ��ʾ", "��û���㹻��Ȩ����������ҳ��", "classManager.action");
			return "message";
		}
		if (stuName!=null && stuNum != null) {
			if (stuName.equals("") || stuNum.equals("")) {
				messageModel.setData("ϵͳ��ʾ", "�����Ƿ�", "classManager.action");
				return "message";
			}
			if (studentService.addStudent(stuName, stuNum)) {
				userService.addStudent(stuNum, stuName);
				int userId = userService.getUserByUsername(stuNum).getUserId();
				choiceclassService.add(cId, userId);
				messageModel.setData("ϵͳ��ʾ", "�û���ӳɹ�", "showStudent.action?classId=" + classId);
				return "message";
			}else {
				int userId = userService.getUserByUsername(stuNum).getUserId();
				choiceclassService.add(cId, userId);
				messageModel.setData("ϵͳ��ʾ", "�û���ӳɹ�", "showStudent.action?classId=" + classId);
				return "message";
			}
			
		}
		return SUCCESS;
	}
	
}
