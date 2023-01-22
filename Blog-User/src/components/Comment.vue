<template>
  <div class="ui teal segment">
    <div class="ui threaded comments" style="max-width: 100%;">
      <h3 class="ui dividing header">评论</h3>
      <!-- 遍历生成评论 -->
      <div class="commentList">
        <div v-for="comment in commentList" :key="comment.id" class="comment">
          <!-- 头像 -->
          <a class="avatar"> <img :src="comment.avatar"/></a>
          <!-- 内容 -->
          <div class="content">
            <a class="author"
              >{{ comment.nickname }}
              <div
                class="ui mini basic teal left pointing label h-padded-mini"
                v-if="comment.createBy == authorId"
              >
                博主
              </div>
            </a>
            <div class="metadata">
              <span class="date">{{ comment.createTime }}</span>
            </div>
            <div class="text">
              <p>{{ comment.content }}</p>
            </div>
            <div class="actions">
              <a
                class="reply"
                @click="
                  reply(
                    comment.id,
                    comment.id,
                    comment.createBy,
                    comment.nickname
                  )
                "
                >回复</a
              >
            </div>
          </div>
          <!-- 子评论 -->
          <div
            v-for="child in comment.children"
            :key="child.id"
            class="comments"
          >
            <div class="comment">
              <a class="avatar"><img :src="child.avatar"/></a>
              <div class="content">
                <div
                  class="ui mini basic teal right pointing label h-padded-mini"
                  v-if="child.createBy == authorId"
                >
                  博主
                </div>
                <a class="author"
                  >{{ child.nickname }}
                  <div class="ui mini basic blue label h-padded-mini">
                    回复 @{{ comment.nickname }}
                  </div>
                </a>
                <div class="metadata">
                  <span class="date">{{ child.createTime }}</span>
                </div>
                <div class="text">{{ child.content }}</div>
                <div class="actions">
                  <a
                    class="reply"
                    @click="
                      reply(
                        child.id,
                        comment.id,
                        child.createBy,
                        child.nickname
                      )
                    "
                    >回复</a
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 加载更多评论 -->
      <div
        style="display: flex;align-items: center;justify-content: center;margin: 10px 0;cursor: pointer"
      >
        <div
          style="color: #777777"
          v-if="hasNext"
          @click="getCommentList(index + 1)"
        >
          加载更多评论<i class="angle double down icon"></i>
        </div>
      </div>
      <!-- 回复评论模块 -->
      <div class="ui form">
        <div class="field">
          <textarea
            v-model="commentDTO.content"
            placeholder="输入评论信息...."
            ref="commentText"
          ></textarea>
        </div>

        <div class="fields">
          <div
            class="file h-mobile-wide h-margin-bottom-mini"
            v-if="showCancleReply"
          >
            <div class="ui h-mobile-wide  button" @click="cancelReply()">
              <span>取消回复</span>
            </div>
          </div>
          <div class="file h-mobile-wide h-margin-bottom-mini">
            <div
              class="ui teal h-mobile-wide labeled submit icon button"
              @click="addComment()"
            >
              <i class="icon edit"></i>评论
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import commentAPI from "@/api/comment";

const commentData = {
  // 文章id
  articleId: undefined,
  // 根评论id
  rootId: -1,
  // 评论内容
  content: "",
  // 回复用户id
  toCommentUserId: -1,
  // 回复评论id
  toCommentId: -1
};

export default {
  // 数据区
  data() {
    return {
      // 评论内容
      commentDTO: { ...commentData },
      // 评论列表
      commentList: [],
      // 是否显示取消评论
      showCancleReply: false,
      // 当前页
      index: 1,
      // 每页记录数
      limit: 5,
      // 是否有下一页
      hasNext: false
    };
  },
  props: {
    articleId: {
      type: String
    },
    authorId: {
      type: String
    }
  },
  // 渲染前执行
  created() {
    this.getCommentList(this.index);
  },
  watch: {
    showCancleReply(newValue, oldValue) {
      if (!newValue) {
        this.$refs.commentText.placeholder = "请输入评论";
        this.commentDTO = { ...commentData };
      }
    }
  },
  // 方法区
  methods: {
    // 获取评论内容
    getCommentList(index) {
      this.index = index;
      commentAPI
        .pageQueryArticleComment(this.index, this.limit, this.articleId)
        .then(res => {
          this.commentList = this.commentList.concat(res.data.records);
          // 是否有下一页
          this.hasNext = res.data.hasNext;
        });
    },
    // 回复评论
    reply(toCommentId, rootId, toCommentUserId, nickname) {
      this.commentDTO.toCommentId = toCommentId;
      this.commentDTO.rootId = rootId;
      this.commentDTO.toCommentUserId = toCommentUserId;
      this.$refs.commentText.placeholder = "@" + nickname + ":";
      this.showCancleReply = true;
    },
    // 取消回复
    cancelReply() {
      this.showCancleReply = false;
    },
    // 添加评论
    addComment() {
      this.commentDTO.articleId = this.articleId;
      commentAPI.addComment(this.commentDTO).then(res => {
        this.commentDTO = { ...commentData };
        this.showCancleReply = false;
        this.$emit("transfer", new Date().getTime());
      });
    }
  }
};
</script>

<style scoped></style>
