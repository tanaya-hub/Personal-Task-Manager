import { useEffect, useState } from "react";
import API from "../api/api";
import "../styles/Dashboard.css";

function Dashboard() {

  const [tasks, setTasks] = useState([]);

  const [newTask, setNewTask] = useState({
    title: "",
    description: "",
  });

  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    try {
      const response = await API.get(`/tasks`);
      setTasks(response.data);
    } catch (error) {
      console.error("Error fetching tasks", error);
    }
  };

  const handleChange = (e) => {
    setNewTask({
      ...newTask,
      [e.target.name]: e.target.value,
    });
  };

  const createTask = async (e) => {
    e.preventDefault();

    try {
      await API.post("/tasks", {
        title: newTask.title,
        description: newTask.description
      });

      alert("Task Created ✅");

      fetchTasks();

      setNewTask({
        title: "",
        description: ""
      });

    } catch (error) {
      console.error("Error creating task", error);
    }
  };

  const updateStatusLocal = async (taskId, newStatus) => {
    try {
      await API.put(`/tasks/${taskId}/status`, {
        status: newStatus,
      });

      setTasks(prev =>
        prev.map(task =>
          task.id === taskId ? { ...task, status: newStatus } : task
        )
      );

    } catch (error) {
      console.error("Error updating status", error);
    }
  };

  const deleteTask = async (taskId) => {
    try {
      await API.delete(`/tasks/${taskId}`);

      setTasks(prev => prev.filter(task => task.id !== taskId));

    } catch (error) {
      console.error("Error deleting task", error);
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    window.location.href = "/login";
  };

  // ✅ FIX: function defined BEFORE usage
  const renderTask = (task) => (
  <div key={task.id} className="task-card">

    <h6>{task.title}</h6>
    <p>{task.description}</p>

    <select
      className="form-select mb-2"
      value={task.status}
      onChange={(e) => updateStatusLocal(task.id, e.target.value)}
    >
      <option value="TODO">TODO</option>
      <option value="IN_PROGRESS">IN PROGRESS</option>
      <option value="DONE">DONE</option>
    </select>

    <button
      className="btn btn-danger btn-sm w-100"
      onClick={() => deleteTask(task.id)}
    >
      Delete
    </button>

  </div>
);

    return (
  <div className="dashboard-container">
    <div className="dashboard-box">

      {/* LOGOUT */}
      <button className="btn btn-dark logout-btn" onClick={logout}>
        Logout
      </button>

      {/* HEADER */}
      <div className="dashboard-header">
        📋 Task Board
      </div>

      {/* WELCOME */}
      <div className="welcome-text">
        Welcome,
      </div>

      {/* CREATE TASK */}
      <div className="task-form">
        <h5>Create Task</h5>

        <form onSubmit={createTask}>
          <input
            type="text"
            name="title"
            className="form-control task-input"
            placeholder="Task Title"
            value={newTask.title}
            onChange={handleChange}
            required
          />

          <textarea
            name="description"
            className="form-control task-input"
            placeholder="Description"
            value={newTask.description}
            onChange={handleChange}
            required
          />

          <button className="btn btn-success mt-2">Add Task</button>
        </form>
      </div>

      {/* TASK BOARD */}
      <div className="task-board">

        {/* TODO */}
        <div className="task-column">
          <h6 className="text-center">TODO</h6>
          {tasks.filter(t => t.status === "TODO").map(task => renderTask(task))}
        </div>

        {/* IN PROGRESS */}
        <div className="task-column">
          <h6 className="text-center">IN PROGRESS</h6>
          {tasks.filter(t => t.status === "IN_PROGRESS").map(task => renderTask(task))}
        </div>

        {/* DONE */}
        <div className="task-column">
          <h6 className="text-center">DONE</h6>
          {tasks.filter(t => t.status === "DONE").map(task => renderTask(task))}
        </div>

      </div>

    </div>
  </div>
);
}

export default Dashboard;