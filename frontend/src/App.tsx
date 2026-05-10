import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import Navbar from './components/Navbar';
import HomePage from './pages/HomePage';
import PgDetailPage from './pages/PgDetailPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import OwnerDashboard from './pages/OwnerDashboard';
import ProfilePage from './pages/ProfilePage';

const App: React.FC = () => {
  return (
    <AuthProvider>
      <Router>
        <div className="min-h-screen bg-background text-text">
          <Navbar />
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/pg/:id" element={<PgDetailPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/dashboard" element={<OwnerDashboard />} />
            <Route path="/profile" element={<ProfilePage />} />
          </Routes>
          
          <footer className="py-12 border-t border-border mt-20">
            <div className="container text-center text-text-muted">
              <p>&copy; {new Date().getFullYear()} PG Finder. All rights reserved.</p>
              <div className="mt-4 flex justify-center gap-6">
                <a href="#" className="hover:text-primary transition-colors">Terms</a>
                <a href="#" className="hover:text-primary transition-colors">Privacy</a>
                <a href="#" className="hover:text-primary transition-colors">Cookies</a>
              </div>
            </div>
          </footer>
        </div>
      </Router>
    </AuthProvider>
  );
};

export default App;
