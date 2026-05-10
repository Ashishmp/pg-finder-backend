import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Mail, Lock, User, Phone, ArrowRight, AlertCircle, Briefcase, UserPlus } from 'lucide-react';

const RegisterPage: React.FC = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    phone: '',
    role: 'USER'
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await register(formData);
      navigate('/login');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Registration failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  return (
    <div className="pt-32 pb-16 min-h-screen flex items-center justify-center bg-gradient px-4 relative overflow-hidden">
      {/* Decorative Glows */}
      <div className="absolute top-1/4 -right-20 w-96 h-96 bg-primary/10 blur-[120px] rounded-full"></div>
      <div className="absolute bottom-1/4 -left-20 w-96 h-96 bg-accent/10 blur-[120px] rounded-full"></div>

      <div className="glass w-full max-w-2xl p-12 rounded-[40px] animate-fade-in border-glass-border shadow-2xl relative z-10">
        <div className="text-center mb-12">
          <div className="w-16 h-16 bg-primary/10 rounded-2xl flex items-center justify-center mx-auto mb-6">
            <UserPlus size={32} className="text-primary" />
          </div>
          <h1 className="text-4xl font-bold mb-3 tracking-tight">Create Account</h1>
          <p className="text-text-muted font-medium">Join the premium PG Finder community</p>
        </div>

        {error && (
          <div className="mb-8 p-4 bg-red-500/10 border border-red-500/20 rounded-2xl flex items-center gap-3 text-red-400 text-sm font-medium">
            <AlertCircle size={18} />
            <span>{error}</span>
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-8">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div className="space-y-2.5">
              <label className="text-sm font-bold text-text-muted ml-1 uppercase tracking-wider">Full Name</label>
              <div className="relative">
                <User className="absolute left-5 top-1/2 -translate-y-1/2 text-primary/50" size={20} />
                <input 
                  type="text" 
                  name="name"
                  required
                  value={formData.name}
                  onChange={handleChange}
                  className="w-full bg-surface-light/30 border border-border rounded-2xl py-4 pl-14 pr-5 focus:border-primary focus:ring-1 focus:ring-primary transition-all text-text placeholder:text-text-muted/30 font-medium"
                  placeholder="John Doe"
                />
              </div>
            </div>

            <div className="space-y-2.5">
              <label className="text-sm font-bold text-text-muted ml-1 uppercase tracking-wider">Phone Number</label>
              <div className="relative">
                <Phone className="absolute left-5 top-1/2 -translate-y-1/2 text-primary/50" size={20} />
                <input 
                  type="tel" 
                  name="phone"
                  required
                  value={formData.phone}
                  onChange={handleChange}
                  className="w-full bg-surface-light/30 border border-border rounded-2xl py-4 pl-14 pr-5 focus:border-primary focus:ring-1 focus:ring-primary transition-all text-text placeholder:text-text-muted/30 font-medium"
                  placeholder="+91 98765 43210"
                />
              </div>
            </div>
          </div>

          <div className="space-y-2.5">
            <label className="text-sm font-bold text-text-muted ml-1 uppercase tracking-wider">Email Address</label>
            <div className="relative">
              <Mail className="absolute left-5 top-1/2 -translate-y-1/2 text-primary/50" size={20} />
              <input 
                type="email" 
                name="email"
                required
                value={formData.email}
                onChange={handleChange}
                className="w-full bg-surface-light/30 border border-border rounded-2xl py-4 pl-14 pr-5 focus:border-primary focus:ring-1 focus:ring-primary transition-all text-text placeholder:text-text-muted/30 font-medium"
                placeholder="name@example.com"
              />
            </div>
          </div>

          <div className="space-y-2.5">
            <label className="text-sm font-bold text-text-muted ml-1 uppercase tracking-wider">Password</label>
            <div className="relative">
              <Lock className="absolute left-5 top-1/2 -translate-y-1/2 text-primary/50" size={20} />
              <input 
                type="password" 
                name="password"
                required
                value={formData.password}
                onChange={handleChange}
                className="w-full bg-surface-light/30 border border-border rounded-2xl py-4 pl-14 pr-5 focus:border-primary focus:ring-1 focus:ring-primary transition-all text-text placeholder:text-text-muted/30 font-medium"
                placeholder="••••••••"
              />
            </div>
          </div>

          <div className="space-y-2.5">
            <label className="text-sm font-bold text-text-muted ml-1 uppercase tracking-wider">Account Type</label>
            <div className="relative">
              <Briefcase className="absolute left-5 top-1/2 -translate-y-1/2 text-primary/50" size={20} />
              <select 
                name="role"
                value={formData.role}
                onChange={handleChange}
                className="w-full bg-surface-light/30 border border-border rounded-2xl py-4 pl-14 pr-5 focus:border-primary focus:ring-1 focus:ring-primary transition-all appearance-none text-text font-medium cursor-pointer"
              >
                <option value="USER">Looking for PG (User)</option>
                <option value="OWNER">PG Owner / Provider</option>
              </select>
            </div>
          </div>

          <button 
            type="submit" 
            disabled={loading}
            className="btn btn-primary w-full py-5 rounded-2xl flex items-center justify-center gap-3 text-lg mt-4 shadow-xl shadow-primary/20"
          >
            {loading ? 'Creating Your Account...' : 'Join the Community'}
            {!loading && <ArrowRight size={22} />}
          </button>
        </form>

        <p className="mt-12 text-center text-text-muted font-medium">
          Already have an account? <Link to="/login" className="text-primary font-bold hover:text-accent transition-colors ml-1">Sign in</Link>
        </p>
      </div>
    </div>
  );
};

export default RegisterPage;
