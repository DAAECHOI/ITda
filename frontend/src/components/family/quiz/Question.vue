<template>
  <v-card class="mx-2 mt-1 mb-5">
    <!-- 문제보기 컴포넌트 -->
    <v-container>
      <v-card color="#FFF9E9" flat>
        <v-card-title style="justify-content: center;">
          <v-card class="mx-5 my-5" color="#FFDD71" flat style="width: 100%;">
            <h3 class="question-title">{{ content }}</h3>
          </v-card>
        </v-card-title>
        
        <!-- 이미지 있을때 보여주고, 아니면 안 보임-->
        <v-img v-if="imageUrl" :src="imageUrl"></v-img>
        <!-- 보기 -->
        <div class="mx-10 my-10">
          <v-row class="question-num">
            <v-col>
              <h3><v-icon color="black">mdi-numeric-1-box</v-icon>{{example1}}</h3>
            </v-col>
            <v-col>
              <h3><v-icon color="black">mdi-numeric-2-box</v-icon>{{example2}}</h3>
            </v-col>
          </v-row>
          <v-row class="question-num">
            <v-col v-if="this.example3 != ''">
              <h3><v-icon color="black">mdi-numeric-3-box</v-icon>{{example3}}</h3>
            </v-col>
            <v-col v-if="this.example4 != ''">
              <h3><v-icon color="black">mdi-numeric-4-box</v-icon>{{example4}}</h3>
            </v-col>
          </v-row>
        </div>

        <!-- 정답 -->
        <div style="text-align-last: center;">
          <v-chip
            class="ma-2 my-5"
            color="#FEA601"
            dark
            label
            large
          >
            <v-icon left>
              mdi-check-circle
            </v-icon>
            정답 : {{ answer }}번
          </v-chip>
        </div>
      </v-card>
    </v-container>

    <!-- 버튼 -->
    <div v-if="this.watchModifyBtn == true" style="text-align-last: center;">
      <v-btn
        class="mx-2 my-10"
        color="#597ED2"
        dark
        @click="goQuizModify"
      >
        수정하기
      </v-btn>
      <v-btn
        class="mx-2 my-10"
        color="#FC5355"
        dark
        @click="deleteQuiz"
      >
        삭제하기
      </v-btn>
    </div>
  </v-card>
</template>

<script>
import axios from "@/service/axios.service.js";

export default {
  name: "Question",
  data () {
    return {
      content:'',
      imageUrl:'',
      example1:'',
      example2:'',
      example3:'',
      example4:'',
      answer:0,
      watchModifyBtn: false,
    }
  },
  created(){
    this.getQuiz();
  },
  methods: {
    getQuiz(){
        axios.get('/qna/one',{
            params:{
                questionId: Number(this.$route.params.questionId)
            }
        }).then((res) => {
            // 문제 제목, 이미지, 보기, 정답 가져오기
            this.content = res.data.questionContent;
            this.imageUrl = res.data.questionImageUrl;
            this.example1 = res.data.example1;
            this.example2 = res.data.example2;
            this.example3 = res.data.example3;
            this.example4 = res.data.example4;
            this.answer = res.data.answer;
            
            // 내가 올린 게시물만 수정 가능
            if (this.$store.state.adminId == res.data.adminId) {
              this.watchModifyBtn = true
            }
            
        }).catch(error => {
            console.log(error);
        });
    },
    goQuizModify() {
      this.$router.push({name: 'QuizItdaModifyQuestion', params: Number(this.$route.params.questionId)})
    },
    goQuizList() {
      this.$router.push({name: 'QuizItdaList'});
    },
    deleteQuiz(){
      axios.delete('/qna',{
            params:{
                questionId: Number(this.$route.params.questionId)
            }
        }).then((res) => {
          if(res.data == 'success')
            this.goQuizList();
        }).catch(error => {
            console.log(error);
        });
    }
  },
}
</script>

<style>
  .question-title {
    margin: inherit;
    text-align: center;
  }
  .v-img {
    display: block;
    width: 100vw;
    height: 56.25vw;
    max-height: 100vh;
  }
  .question-num {
    text-align-last: center;
    text-align: center;
  }
</style>