<template>
  <div class="app-container">
    <!-- 导航栏 -->
    <Header></Header>
    <!-- 友链内容区域 -->
    <div
      class="h-body-bottom h-padded-td-big animate__animated animate__fadeIn "
    >
      <div class="ui container d-web-info-box">
        <!-- 显示各个图片 -->
        <div class="ui stackable grid">
          <div
            v-for="link in passedLinkList"
            :key="link.id"
            class="four wide column"
          >
            <div class="d-web-info-box-item" data-wow-duration="0.4s">
              <div class="d-web-user-info-box">
                <img
                  :src="link.logo"
                  class="avatar-image"
                  width="80px"
                  height="80px"
                />
                <h3>
                  <a :href="link.address">{{ link.name }}</a>
                </h3>
                <span>{{ link.description + "sadasdioubadiudsb" }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 友链评论 -->
    <div class="h-body-bottom animate__animated animate__fadeIn ui container">
      <div class="ui teal segment">
        <div class="ui threaded comments" style="max-width: 100%;">
          <h3 class="ui dividing header">友链申请</h3>
          <!-- 遍历生成评论 -->
          <div class="commentList">
            <div v-for="link in linkList" :key="link.id" class="comment">
              <!-- 头像 -->
              <a class="avatar"> <img :src="link.avatar"/></a>
              <!-- 内容 -->
              <div class="content">
                地址:{{ link.address }},描述:{{ link.description }}
              </div>
            </div>
          </div>
          <!-- 添加友链模块 -->
          <div class="ui form">
            <div class="field">
              <input v-model="linkDTO.name" placeholder="友链名称" />
            </div>

            <div class="field">
              <input v-model="linkDTO.logo" placeholder="友链logo" />
            </div>

            <div class="field">
              <input v-model="linkDTO.address" placeholder="友链地址" />
            </div>

            <div class="field">
              <textarea
                v-model="linkDTO.description"
                placeholder="友链描述"
              ></textarea>
            </div>

            <div class="fields">
              <div
                class="ui teal h-mobile-wide labeled submit icon button"
                @click="addLink()"
              >
                <i class="icon edit"></i>申请
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import linkAPI from "@/api/link";

export default {
  // 友链
  name: "Link",
  components: {
    Header
  },
  // 数据区
  data() {
    return {
      // 所有通过的友链
      passedLinkList: [],
      // 所有友链,用于评论区显示
      linkList: [],
      // 添加友链
      linkDTO: {
        name: "",
        logo: "",
        address: "",
        description: ""
      }
    };
  },
  // 渲染前执行
  created() {
    this.getAllLink();
  },
  // 方法区
  methods: {
    // 获取所有友链
    getAllLink() {
      linkAPI.getAllLink().then(res => {
        this.linkList = res.data;
        // 获取通过的友链
        this.passedLinkList = this.linkList.filter(link => link.status == 0);
      });
    },
    // 添加友链
    addLink() {
      linkAPI.addLink(this.linkDTO).then(res => {
        // 添加成功
        this.linkList = this.linkList.push(this.linkDTO);
      });
    }
  }
};
</script>

<style scoped>
span {
  word-break: break-all;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
}

.h-padded-td-big {
  padding-bottom: 0 !important;
}

.avatar-image {
  width: 80px;
  height: 80px;
}

.d-web-info-box-item {
  width: 100%;
  box-shadow: var(--box-border);
  padding: 15px;
  box-sizing: border-box;
  border-radius: 15px;
  transition: all 0.3s;
  position: sticky;
  top: 80px;
  margin-bottom: 15px;
  background: var(--background);
}

@media only screen and (max-width: 767px) {
  .ui.stackable.grid > .wide.column {
    width: 25% !important;
    margin: 0 0 !important;
    -webkit-box-shadow: none !important;
    box-shadow: none !important;
    padding: 1rem 1rem !important;
  }
}

@media only screen and (max-width: 580px) {
  .ui.stackable.grid > .wide.column {
    width: 33.3% !important;
    margin: 0 0 !important;
    -webkit-box-shadow: none !important;
    box-shadow: none !important;
    padding: 1rem 1rem !important;
  }
}

@media screen and (max-width: 456px) {
  .avatar-image {
    width: 40px !important;
    height: 40px !important;
  }

  .ui.stackable.grid > .wide.column {
    width: 50% !important;
    margin: 0 0 !important;
    -webkit-box-shadow: none !important;
    box-shadow: none !important;
    padding: 1rem 1rem !important;
  }
}
</style>
