import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "../login/Login";
import AppNavbar from "../nav/NavBar";
import RegionSelector from "../regionSelect/Selector";
import { AuthProvider } from "../../contexts/AuthContext";
import ProtectedRoute from "../auth/ProtectedRoute";
import Register from "../login/Register";
import AdminDashboard from "../admin/Dashboard";
import AdminUsers from "../admin/Users";
import LoginRecords from "../admin/LoginList";

function AppRouter() {
  return (
    <AuthProvider>
      <Router>
        <div className="min-h-screen bg-white">
          <AppNavbar />
          <Routes>
            <Route path="/" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route
              path="/Region"
              element={<ProtectedRoute element={<RegionSelector />} />}
            />
            <Route
              path="/admin-dashboard"
              element={<ProtectedRoute element={<AdminDashboard />} adminOnly />}
            />
             <Route
              path="/admin-users"
              element={<ProtectedRoute element={<AdminUsers />} adminOnly />}
            />
            <Route
              path="/admin-users-login"
              element={<ProtectedRoute element={<LoginRecords />} adminOnly />}
            />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default AppRouter;
