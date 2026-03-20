import { useState } from "react";
import API from "../api/api";
import { Link, useNavigate } from "react-router-dom";
import "./auth.css";

function Register() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: "",
    email: "",
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
      await API.post("/auth/register", formData);
      alert("Registration successful");
      navigate("/");
    } catch (error) {
      alert("Registration failed");
      console.error(error);
    }
  };

  return (
  <div className="auth-container">

    <div className="auth-card">

      <h2 className="auth-title">📝 Register</h2>

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
          type="email"
          name="email"
          placeholder="Email"
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
          Register
        </button>

      </form>

      <p className="auth-link">
        Already have an account? <Link to="/login">Login</Link>
      </p>

    </div>

  </div>
);
      
}

export default Register;
