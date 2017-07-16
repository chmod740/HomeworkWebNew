package me.hupeng.homeworkweb.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import me.hupeng.homeworkweb.model.MessageModel;
import me.hupeng.homeworkweb.service.CoursewareService;
import me.hupeng.homeworkweb.service.HomeworkService;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
//ppt�ļ��ϴ���
public class UploadCoursewareAction extends ActionSupport{
	private String allowType="application/vnd.ms-powerpoint,application/x-ppt��application/vnd.openxmlformats-officedocument.presentationml.presentation";
    // ��װ�ϴ��ļ��������
    private File file;
    // ��װ�ϴ��ļ����͵�����
    private String fileContentType;
    // ��װ�ϴ��ļ���������
    private String fileFileName;
    // ��������ע�������
    private String savePath;
    private MessageModel messageModel;
    private CoursewareService coursewareService;
    private String filePath;
    private String fullPath;
    private String classId;
    public File getFile() {
		return file;
	}
    
	public void setFile(File file) {
		this.file = file;
	}
	
	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}


	public MessageModel getMessageModel() {
		return messageModel;
	}

	public void setMessageModel(MessageModel messageModel) {
		this.messageModel = messageModel;
	}

	public CoursewareService getCoursewareService() {
		return coursewareService;
	}

	public void setCoursewareService(CoursewareService coursewareService) {
		this.coursewareService = coursewareService;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	@Override
    public String execute() throws Exception {
		//�û������֤
		Integer classIdInteger;
		messageModel = new MessageModel();
		try {
			classIdInteger = Integer.parseInt(classId);
			if (!(classIdInteger+"").equals(classId)) {
				throw new Exception();
			}
		} catch (Exception e) {
			// TODO: handle exception
			messageModel.setText("ԭ�򣺲����Ƿ�������IP��ַ" + ServletActionContext.getRequest().getRemoteAddr()+"�ѱ�ϵͳ��¼");
			messageModel.setTitle("�ϴ�������ϴ�ʧ��");
			messageModel.setUrl("taskInfo.action");
			return "message";
		}
		
		
		if (fileContentType == null ) {
			messageModel.setTitle("�ϴ�������ϴ�ʧ��");
			messageModel.setText("ԭ������ѡ���ļ����ϴ�");
			messageModel.setUrl("coursewareInfo.action?classId=" + classId);
			return "message";
		}
		if (!allowType.contains(fileContentType)) {
			messageModel.setTitle("�ϴ�������ϴ�ʧ��");
			messageModel.setText("ԭ���ļ����Ͳ�������ǰ������ļ����ͣ�" + allowType + "������Ҫ֧�ָ����ļ����ͣ�����ϵ����Ա��");
			messageModel.setUrl("coursewareInfo.action?classId=" + classId);
			return "message";
		}
		
		SecurityAction securityAction = new SecurityAction();
		if (!securityAction.checkUser()) {
			return LOGIN;
		}
		
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            // �����ļ������
            filePath = getSavePath();
            savePath += "/" + "courseware/"+ classId;
            File tempFile = null;
            tempFile = new File(savePath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
    		}
            fullPath = savePath + "/" + classId + "_" + System.currentTimeMillis() + "." +getFileFileName().split("\\.")[getFileFileName().split("\\.").length-1];
            fos = new FileOutputStream(fullPath);
            // �����ļ��ϴ��� 
            fis = new FileInputStream(getFile());
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            System.out.println("�ļ��ϴ�ʧ��");
            messageModel.setTitle("�ϴ�������ϴ�ʧ��");
			messageModel.setText("ԭ��" + e);
			messageModel.setUrl("coursewareInfo.action?classId=" + classId);
            e.printStackTrace();
			return "message";
        } finally {
            close(fos, fis);
        }
        //����������ݿ�д�����*********************************x
//        homeworkService.add(securityAction.getUserId(),taskIdInteger, fullPath.replace(filePath.replace("uploadFile", ""), ""));
        coursewareService.add(fileFileName, fullPath, securityAction.getUserId(), classIdInteger);
        //homeworkService.add(securityAction.getUserId(),taskIdInteger, fullPath);
        //************************************************
        messageModel.setTitle("�ϴ�������ϴ��ɹ�");
		messageModel.setText("�ϴ��ߣ�" + securityAction.getUsername() + "<br>" + "ʱ�����" + System.currentTimeMillis()/1000 + "<br>" + "�ļ�����" + fileFileName);
		messageModel.setUrl("coursewareInfo.action?classId=" + classId);
        return "message";
    }

    /**
     * �����ϴ��ļ��ı���λ��
     * 
     * @return
     */
    public String getSavePath() throws Exception{

    	if (savePath == null) {
    		 ServletActionContext.getServletContext().getRealPath(savePath);
		}
    	savePath = "/home/HomeworkWeb" +savePath;
        return savePath;
    }

    
    private void close(FileOutputStream fos, FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                System.out.println("FileInputStream�ر�ʧ��");
                e.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                System.out.println("FileOutputStream�ر�ʧ��");
                e.printStackTrace();
            }
        }
    }
}
