import request from '@/utils/request';

const API_PATH = "blog/oss";

export default {
    // 上传头像文件
    uploadImage(imageFile) {
        const uploadData = new FormData();
        uploadData.append("image", imageFile);
        return request({
            url: `${API_PATH}/uploadImage`,
            headers: { 'Content-Type': 'multipart/form-data' },
            method: 'POST',
            data: uploadData
        })
    }
}