<template>
  <div :class="['message', 'message-' + type]" v-if="show">
    <div class="message-text">
      <div class="message-title" v-if="title !== ''">{{ title }}</div>
      <div class="message-content">提示信息：{{ message }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "TMessage",

  props: {
    message: {
      type: String,
      default: ""
    },
    title: {
      type: String,
      default: ""
    },
    type: {
      type: String,
      default: "success"
    },
    duration: {
      type: Number,
      default: 3000
    }
  },

  data() {
    return {
      show: false
    };
  },

  mounted() {
    this.show = true;
    let timer = setTimeout(() => {
      if (this.show) {
        this.close(timer);
      }
    }, this.duration);
  },

  methods: {
    close(timer) {
      this.show = false;
      clearTimeout(timer);
      timer = null;
    }
  }
};
</script>
<style scoped>
.message {
  position: fixed;
  right: 10px;
  top: 20px;
  z-index: 999;
  width: 320px;
  padding: 10px;
  border-radius: 5px;
  display: flex;
  justify-content: space-between;
}

.message-title {
  margin-bottom: 4px;
  font-size: 14px;
  font-weight: bold;
}

.message-content {
  font-size: 12px;
}

.message-actions {
  display: flex;
  justify-items: center;
}

.message-success {
  background-color: #f0f9eb;
  border: 1px solid #67c23a;
}

.message-success .message-title,
.message-success .message-content {
  color: #67c23a;
}

.message-warning {
  background-color: #fdf6ec;
  border: 1px solid #e6a23c;
}

.message-error .message-title,
.message-error .message-content {
  color: #e6a23c;
}

.message-error {
  background-color: #fef0f0;
  border: 1px solid #f56c6c;
}

.message-error .message-title,
.message-error .message-content {
  color: #f56c6c;
}
</style>
