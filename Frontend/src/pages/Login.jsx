import { useState } from "react";
import API from "../api/api";
import { Link, useNavigate } from "react-router-dom";
import "./auth.css";
function Login() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
  e.preventDefault();

  try {
    const response = await API.post("/auth/login", formData);

    // ✅ SAVE TOKEN + USER ID
    localStorage.setItem("token", response.data.token);
    localStorage.setItem("userId", response.data.userId);

    alert("Login successful");

    navigate("/dashboard");

  } catch (error) {
    alert("Login failed");
    console.error(error);
  }
};

  return (
  <div className="auth-container">

    <div className="auth-card">

      <h2 className="auth-title">🔐 Login</h2>

      <form onSubmit={handleSubmit}>

        <input
          type="text"
          name="username"
          placeholder="Username"
          className="auth-input"
          onChange={handleChange}
          required
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          className="auth-input"
          onChange={handleChange}
          required
        />

        <button type="submit" className="auth-btn">
          Login
        </button>

      </form>

      <p className="auth-link">
        Don't have an account? <Link to="/register">Register</Link>
      </p>

    </div>

  </div>
);
}

export default Login;