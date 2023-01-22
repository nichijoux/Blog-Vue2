<template>
  <div class="app-container">
    <el-form
      ref="articleMDForm"
      :model="articleMDData"
      :rules="articleFormRules"
      label-width="90px"
    >
      <el-row :gutter="20">
        <!-- 文章标题 -->
        <el-col :span="10">
          <el-form-item label="文章标题" prop="title">
            <el-input
              v-model="articleMDData.title"
              placeholder="请输入文章标题"
              maxlength="30"
            />
          </el-form-item>
        </el-col>
        <!-- 文章标签 -->
        <el-col :span="6">
          <el-form-item label="标签">
            <el-select
              v-model="articleMDData.tagIdList"
              placeholder="请选择"
              multiple
            >
              <el-option
                v-for="tag in tagList"
                :key="tag.id"
                :label="tag.name"
                :value="tag.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <!-- 文章是否允许评论 -->
        <el-col :span="4">
          <el-form-item label="允许评论">
            <el-radio-group v-model="articleMDData.commentable">
              <el-radio :key="true" :label="true">允许</el-radio>
              <el-radio :key="false" :label="false">禁止</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <!-- 是否推荐 -->
        <el-col :span="4">
          <el-form-item label="是否推荐">
            <el-radio-group v-model="articleMDData.recommend">
              <el-radio :key="true" :label="true">是</el-radio>
              <el-radio :key="false" :label="false">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <!-- 文章摘要 -->
        <el-col :span="24">
          <el-form-item label="文章摘要" prop="summary">
            <el-input v-model="articleMDData.summary" type="textarea" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20" />

      <el-row :gutter="20">
        <!-- 文章封面 -->
        <el-col :span="18">
          <el-form-item label="封面" prop="cover">
            <el-upload
              class="cover-uploader"
              list-type="picture-card"
              accept="image/*"
              action
              :limit="1"
              :with-credentials="true"
              :class="{ disabled: uploadDisabled }"
              :file-list="coverList"
              :before-upload="beforeCoverUpload"
              :http-request="uploadCover"
              :on-remove="coverRemove"
              :on-exceed="coverExceed"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
        </el-col>
        <!-- 保存或发布 -->
        <el-col :span="6">
          <el-form-item>
            <el-button
              type="primary"
              size="medium"
              @click="addOrUpdateArticle()"
              style="width: 100px"
              >{{ articleMDData.id ? "更新" : "发布" }}</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              type="danger"
              @click="saveDraft()"
              style="width: 100px"
              >保存草稿</el-button
            >
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 文章内容 -->
      <el-row>
        <mavon-editor
          ref="md"
          v-model="articleMDData.content"
          @imgAdd="addImage"
        />
      </el-row>
    </el-form>
  </div>
</template>

<script>
import articleAPI from "@/api/article";
import tagAPI from "@/api/tag";
import ossAPI from "@/api/oss";

const articleFormData = {
  // 文章id
  id: undefined,
  // 文章标题
  title: "",
  // 文章摘要
  summary: "",
  // 文章内容
  content: "",
  // 封面地址
  cover: "",
  // 是否发表
  status: true,
  // 是否可评论
  commentable: true,
  // 是否推荐
  recommend: false,
  // 标签id列表
  tagIdList: [],
};

export default {
  // 数据区
  data() {
    return {
      // 文章数据
      articleMDData: { ...articleFormData },
      // 标签列表
      tagList: [],
      // 上传封面
      coverList: [],
      // 是否隐藏上传
      uploadDisabled: false,
      // 表单规则
      articleFormRules: {
        title: [
          { required: true, message: "请输入标题", trigger: "blur" },
          {
            require: true,
            min: 3,
            max: 30,
            message: "标题长度在 3 到 30 个字符",
            trigger: "blur",
          },
        ],
        summary: [
          { required: true, message: "请输入文章摘要", trigger: "blur" },
          {
            require: true,
            min: 3,
            max: 50,
            message: "文章摘要长度在 3 到 50 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  watch: {
    coverList(newValue, oldValue) {
      this.uploadDisabled = newValue.length >= 1;
    },
  },
  // 渲染前执行
  created() {
    // 获取路径id值,根据id获取详情
    if (this.$route.query.id) {
      this.getArticle(this.$route.query.id);
    }
    // 获取tag列表
    this.getTagList();
  },
  // 方法区
  methods: {
    // 根据文章id获取文章信息
    getArticle(id) {
      articleAPI.getArticle(id).then((res) => {
        this.articleMDData = res.data;
        this.coverList.push({ name: "缩略图", url: res.data.cover });
      });
    },
    // 获取tag列表
    getTagList() {
      tagAPI.getAllEnableTag().then((res) => {
        this.tagList = res.data;
      });
    },
    // 添加文章
    addArticle(article) {
      articleAPI
        .addArticle(article)
        .then((res) => {
          this.$message.success("发布文章成功");
          this.$router.push({ path: "/article/management" });
        })
        .catch((err) => {
          this.$message.error("添加文章失败");
        });
    },
    // 更新文章
    updateArticle(article) {
      articleAPI
        .updateArticle(article)
        .then((res) => {
          this.$message.success("修改文章成功");
          this.$router.push({ path: "/article/management" });
        })
        .catch((err) => {
          this.$message.error("修改文章失败");
        });
    },
    // 添加或者更新文章
    addOrUpdateArticle() {
      // 将状态置为发表
      this.articleMDData.status = true;
      if (this.articleMDData.id) {
        // 更新文章
        this.updateArticle(this.articleMDData);
      } else {
        // 添加文章
        this.addArticle(this.articleMDData);
      }
    },
    // 保存文章到草稿箱
    saveDraft() {
      // 将状态置为未发表
      this.articleMDData.status = false;
      // 暂存到数据库
      this.addArticle(this.articleMDData);
    },
    // el-upload所需函数
    // 图片上传之前
    beforeCoverUpload(file) {
      const isImage = file.type.includes("image");
      if (!isImage) {
        this.$message.error("上传文件并非图片");
      }
      const isLt5M = file.size / 1048576 < 5;
      if (!isLt5M) {
        this.$message.error("上传封面图片大小不能超过5MB!");
      }
      return isImage && isLt5M;
    },
    // 上传图片
    uploadCover(image) {
      // 调用OSS API进行上传
      const coverFile = image.file;
      ossAPI
        .uploadImage(coverFile)
        .then((res) => {
          this.articleMDData.cover = res.data;
          this.coverList.push({ name: coverFile.name, url: res.data });
        })
        .catch((err) => {
          this.$message.error(err);
        });
    },
    // 图片移除
    coverRemove(file, fileList) {
      this.coverList.pop();
    },
    // 上传多张图片
    coverExceed() {
      this.$message.error("只能上传一张图片,若要更换图片,请删除原图片");
    },
    // mavon-editor添加图片的函数
    addImage(fileName, imageFile) {
      // 上传到图片服务器
      ossAPI
        .uploadImage(imageFile)
        .then((res) => {
          this.$refs.md.$img2Url(fileName, res.data);
        })
        .catch((err) => {
          this.$message.error(err);
        });
    },
  },
};
</script>

<style>
.disabled .el-upload--picture-card {
  display: none;
}
</style>

<style scoped>
.el-col .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
</style>
