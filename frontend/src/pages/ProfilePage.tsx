import React from 'react';
import { useAuth } from '../context/AuthContext';
import { User, Mail, Phone, Shield, Calendar, MapPin, Edit3, Settings, ShieldCheck } from 'lucide-react';

const ProfilePage: React.FC = () => {
  const { user } = useAuth();

  if (!user) {
    return (
      <div className="pt-40 text-center flex flex-col items-center gap-6">
        <div className="w-20 h-20 bg-surface rounded-full flex items-center justify-center text-text-muted">
           <User size={40} />
        </div>
        <p className="text-xl font-bold">Please login to view your profile.</p>
      </div>
    );
  }

  return (
    <div className="pt-32 pb-20 min-h-screen bg-gradient">
      <div className="container max-w-5xl">
        <div className="flex items-center justify-between mb-12 animate-fade-in">
           <h1 className="text-4xl font-bold tracking-tight">My Account</h1>
           <button className="btn btn-outline gap-2 px-6">
              <Settings size={18} />
              <span>Settings</span>
           </button>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-12 gap-10">
          {/* Profile Card */}
          <div className="lg:col-span-4 animate-fade-in delay-100">
            <div className="glass p-10 rounded-[40px] text-center border-glass-border shadow-2xl relative overflow-hidden">
              <div className="absolute top-0 left-0 w-full h-32 bg-gradient-to-br from-primary/20 to-accent/20 -z-10"></div>
              
              <div className="relative inline-block mb-6">
                <div className="w-32 h-32 bg-gradient-to-br from-primary to-accent rounded-[40px] flex items-center justify-center text-white text-4xl font-bold shadow-2xl shadow-primary/30">
                  {user.name.charAt(0)}
                </div>
                <button className="absolute -bottom-2 -right-2 w-10 h-10 bg-surface rounded-xl border-2 border-background flex items-center justify-center text-primary hover:scale-110 transition-transform shadow-lg">
                   <Edit3 size={18} />
                </button>
              </div>

              <h2 className="text-2xl font-bold tracking-tight mb-1">{user.name}</h2>
              <p className="text-primary font-bold text-sm uppercase tracking-widest mb-8">{user.role}</p>
              
              <div className="space-y-3">
                <div className="flex items-center gap-3 px-5 py-3 rounded-2xl bg-green-500/10 text-green-400 border border-green-500/20 text-sm font-bold w-full justify-center">
                  <ShieldCheck size={16} />
                  <span>Verified Identity</span>
                </div>
                <div className="flex items-center gap-3 px-5 py-3 rounded-2xl bg-primary/10 text-primary border border-primary/20 text-sm font-bold w-full justify-center">
                  <Calendar size={16} />
                  <span>Member for 2 years</span>
                </div>
              </div>
            </div>
          </div>

          {/* Details Card */}
          <div className="lg:col-span-8 animate-fade-in delay-200">
            <div className="glass p-10 rounded-[40px] border-glass-border shadow-2xl">
              <div className="flex items-center justify-between mb-10">
                 <h3 className="text-2xl font-bold tracking-tight">Personal Information</h3>
                 <button className="text-primary font-bold text-sm flex items-center gap-2 hover:text-accent transition-colors">
                   <Edit3 size={16} />
                   Edit Details
                 </button>
              </div>
              
              <div className="grid grid-cols-1 md:grid-cols-2 gap-x-12 gap-y-10">
                <div className="flex items-start gap-5">
                  <div className="w-12 h-12 bg-surface-light rounded-2xl flex items-center justify-center text-primary shadow-sm border border-border/30">
                    <User size={22} />
                  </div>
                  <div>
                    <p className="text-xs text-text-muted uppercase tracking-widest font-bold mb-1">Full Name</p>
                    <p className="text-lg font-bold tracking-tight">{user.name}</p>
                  </div>
                </div>

                <div className="flex items-start gap-5">
                  <div className="w-12 h-12 bg-surface-light rounded-2xl flex items-center justify-center text-secondary shadow-sm border border-border/30">
                    <Mail size={22} />
                  </div>
                  <div>
                    <p className="text-xs text-text-muted uppercase tracking-widest font-bold mb-1">Email Address</p>
                    <p className="text-lg font-bold tracking-tight">{user.email}</p>
                  </div>
                </div>

                <div className="flex items-start gap-5">
                  <div className="w-12 h-12 bg-surface-light rounded-2xl flex items-center justify-center text-accent shadow-sm border border-border/30">
                    <Phone size={22} />
                  </div>
                  <div>
                    <p className="text-xs text-text-muted uppercase tracking-widest font-bold mb-1">Phone Number</p>
                    <p className="text-lg font-bold tracking-tight">{user.phone || "Not provided"}</p>
                  </div>
                </div>

                <div className="flex items-start gap-5">
                  <div className="w-12 h-12 bg-surface-light rounded-2xl flex items-center justify-center text-primary shadow-sm border border-border/30">
                    <MapPin size={22} />
                  </div>
                  <div>
                    <p className="text-xs text-text-muted uppercase tracking-widest font-bold mb-1">Preferred Location</p>
                    <p className="text-lg font-bold tracking-tight">Bangalore, KA</p>
                  </div>
                </div>
              </div>

              <div className="mt-12 pt-10 border-t border-border/50 flex flex-col md:flex-row items-center justify-between gap-6">
                <div className="flex flex-col gap-1">
                   <p className="font-bold text-lg">Account Security</p>
                   <p className="text-text-muted text-sm font-medium">Last login was 2 hours ago from Bangalore</p>
                </div>
                <button className="btn btn-outline text-red-400 border-red-400/20 hover:bg-red-400/10 hover:border-red-400/40">
                  Deactivate Account
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
