<template>
  <v-app>
    <!-- 로그인 페이지 -->
    <FamilyAppBar />
    <v-container class="my-15" style="text-align: -webkit-center;">
      <v-card class="login-card">
        <v-img
          class="login-img"
          :src="require('@/assets/family/itda2.png')"
        ></v-img>
        <!-- 로그인 폼 -->
        <v-form class="mx-10 my-5" ref="form" v-model="valid" lazy-validation>
          <v-text-field
            v-model="member.email"
            :rules="emailRules"
            label="이메일"
            required
            type="email"
            color="#FEA601"
            outlined
            dense
          ></v-text-field>
          <v-text-field
            :rules="passwordRules"
            required
            v-model="member.password"
            label="비밀번호"
            type="password"
            color="#FEA601"
            outlined
            dense
            @keyup.enter="login"
          ></v-text-field>
          <v-btn
            style="width:100%; height:40px; margin-top: 20px;"
            dark
            color="#FEA601"
            @click="login"
            >로그인
          </v-btn>
        </v-form>
        <div>
          회원이 아니신가요?
          <v-btn
            class="mx-2" 
            text color="#FC5355"
            @click="goSignUp"
          >회원가입
          </v-btn>
        </div>
      </v-card>
    </v-container>

    <!-- 모달창 -->
    <v-dialog
      v-model="dialog"
      max-width="300"
    >
      <v-card>
        <v-card-title style="justify-content: center; color: #FEA601;">
          <h2 class="modalFont my-2"></h2>
        </v-card-title>
        <v-card-text 
          class="modalFont my-2" 
          style="text-align-last: center;"
        >
          <h4>로그인에 실패하였습니다.</h4>
          <h4>이메일과 비밀번호를 다시 확인해 주세요.</h4>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            class="modalFont"
            color="#FEA601"
            dark
            block
            @click="notLogin"
          >
            확인
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-app>
</template>

<script>
import axios from "@/service/axios.service.js";
import FamilyAppBar from '@/components/family/FamilyAppBar.vue'

export default {
  name: "Login",
  components: {
    FamilyAppBar,
  },
  data() {
    return {
      valid: true,
      emailRules: [
          (v) => !!v || "이메일을 입력해주세요",
          (v) => /.+@.+\..+/.test(v) || "유효한 이메일이 아닙니다",
      ],
      passwordRules: [
        (v) => !!v || "비밀번호를 입력해주세요",
        // (v) => (v && v.length >= 8 && v.length <= 20) || "8~20자리 입력",
        (v) => /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d$@$!%*#?&]{8,20}$/.test(v) || "문자, 숫자 조합 8~20자리 입력",
      ],
      member: {
        email: "",
        password: "",
      },
      dialog: false,
    };
  },
  methods: {
    login(){
      if(this.$refs.form.validate()) {
      axios.post("/admin/login", {
        adminEmail: this.member.email,
        adminPwd: this.member.password,
      }).then((res) => {
        console.log(res.data);
        if(res.data == "success") {
          // 로그인에 성공하였습니다.
          axios.get("/admin/email",{
            params:{
              adminEmail: this.member.email
            }
          }).then((res2) => {
            this.$store.commit("adminLogin", res2.data.adminId);
            this.getUserId()
            this.$router.push({name: 'Main'});
          }).catch(error => {
            console.log(error);
          })
        }
        else {
          // 로그인 실패
          this.dialog = true
        }
      });
      }
    },
    goSignUp() {
      this.$router.go(this.$router.push({name: 'SignUp'}))
    },
    getUserId() {
      axios.get('/useradmin/admin', {
        params: {
          adminId: Number(this.$store.state.adminId)
        }
      }).then((res) => {
        if(res.data != "") {
          this.$store.commit("userLogin",res.data[0].userId)
        }
      })
    },
    notLogin() {
      this.dialog = false
    },
  },
}
</script>

<style>
  .login-card {
    width: 450px;
    height: 500px;
  }
  .login-img {
    height: 190px !important;
  }
</style>