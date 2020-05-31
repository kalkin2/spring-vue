<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="register-form">
        <!--logo-->
        <div class="logo-wrapper">
          <img class="logo" src="/static/images/logo.png">
          <div class="tagline">Open source task management tool</div>
        </div>
        <!--form -->
        <form @submit.prevent="submitForm">
          <div v-show="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
          <div class="form-group">
            <label for="username">이름 </label>
            <input type="text" class="form-control" id="username" v-model="form.username">
            <div class="field-error" v-if="$v.form.username.$dirty">
              <div class="error"  v-if="!$v.form.username.required">이름을 입력해 주세요</div>
              <div class="error" v-if="!$v.form.username.alphaNum">이름은 문자 혹시 숫자만 가능합니다</div>
              <div class="error" v-if="!$v.form.username.minLength">이름은 적어도 {{ $v.form.username.$params.minLength.min }} 글자 입니다.</div>
              <div class="error" v-if="!$v.form.username.maxLength">이름은 최대 {{ $v.form.username.$params.maxLength.max }} 글자 입니다.</div>
            </div>
          </div>
          <div class="form-group">
            <label for="emailAddress">이메일주소</label>
            <input type="email" class="form-control" id="emailAddress" v-model="form.emailAddress">
            <div class="field-error" v-if="$v.form.emailAddress.$dirty">
              <div class="error" v-if="!$v.form.emailAddress.required">이메일을 입력해 주세요.</div>
              <div class="error" v-if="!$v.form.emailAddress.email">이메일 형식이 아닙니다.</div>
              <div class="error" v-if="!$v.form.emailAddress.maxLength">이메일 길이가 너무 깁니다. 최대 {{ $v.form.emailAddress.$params.maxLength.max }} 글자 입니다.</div>
            </div>
          </div>
          <div class="form-group">
            <label for="password">패스워드</label>
            <input type="password" class="form-control" id="password"  v-model="form.password">
            <div class="field-error" v-if="$v.form.password.$dirty">
              <div class="error" v-if="!$v.form.password.required">패스워드를  입력해 주세요.</div>
              <div class="error" v-if="!$v.form.password.minLength">패스워드가 너무 짧습니다. {{ $v.form.password.$params.minLength.min }} </div>
              <div class="error" v-if="!$v.form.password.maxLength">이메일 길이가 너무 깁니다. 최대 {{ $v.form.password.$params.maxLenth.max }} 글자 입니다.</div>
            </div>
          </div>
          <button type="submit" class="btn btn-primary btn-block">회원가입</button>
          <p class="accept-terms text-muted">By clicking "회원 가입"<a href="#">terms of service</a> and <a href="#">privacy policy</a>.</p>
        </form>
      </div>
    </div>
    <footer class="footer">
      <span class="copyright">&copy; 2018 taskAgile.com</span>
      <ul class="footer-links list-inline float-right">
        <li class="list-inline-item"><a href="#">about</a></li>
        <li class="list-inline-item"><a href="#">Terms of Service</a></li>
        <li class="list-inline-item"><a href="#">Privacy Policy</a></li>
        <li class="list-inline-item"><a href="#">Github</a></li>
      </ul>
    </footer>
  </div>
</template>
<script>
import { required, email, minLength, maxLength, alphaNum } from 'vuelidate/lib/validators'
import registrationService from '@/services/registration'

export default {
  name: 'RegisterPage',
  data: function () {
    return {
      form: {
        username: '',
        emailAddress: '',
        password: ''
      },
      errorMessage: ''
    }
  },
  validations: {
    form: {
      username: {
        required,
        minLength: minLength(2),
        maxLength: maxLength(50),
        alphaNum
      },
      emailAddress: {
        required,
        email,
        maxLength: maxLength(30)
      },
      password: {
        required,
        minLength: minLength(6),
        maxLength: maxLength(30)

      }
    }
  },
  methods: {
    submitForm () {
      this.$v.$touch()
      if (this.$v.$invalid) {
        return
      }

      registrationService.register(this.form).then(() => {
        this.$router.push({ name: 'LoginPage' })
      }).catch((error) => {
        this.errorMessage = 'Failed to register user' + error.message
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  max-width: 900px;
}
.register-form {
  margin-top: 50px;
  max-width: 320px;
}
.logo-wrapper {
  text-align: center;
  margin-bottom: 40px;
  .tagline {
    line-height: 180%;
    color: #666;
  }
  .logo {
    max-width: 150px;
    margin: 0 auto;
  }
}
.register-form {
  .form-group label {
    font-weight: bold;
    color: #555;
  }
  .accept-terms {
    margin: 20px 0 40px 0;
  }
}
.footer {
  width: 100%;
  font-size: 13px;
  color: #666;
  line-height: 40px;
  border-top: 1px solid #ddd;
  margin-top: 50px;
  .list-inline-item {
    margin-right: 10px;
  }
  a {
    color: #666;
  }
}
</style>
