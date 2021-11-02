package id.jyotisa.praktikumprogmob2021.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Job implements Parcelable {
    String companyName, jobTitle, jobDesc, country, jobType, benefits;
    Integer salary;

    public Job(String companyName, String jobTitle, String jobDesc, String country, String jobType, Integer salary, String benefits){
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.jobDesc = jobDesc;
        this.country = country;
        this.jobType = jobType;
        this.salary = salary;
        this.benefits = benefits;
    }

    public String getCompanyName() { return companyName; }
    public String getJobTitle() { return jobTitle; }
    public String getJobDesc() { return jobDesc; }
    public String getCountry() { return country; }
    public String getJobType() { return jobType; }
    public Integer getSalary() { return salary; }
    public String getBenefits() { return benefits; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.companyName);
        dest.writeString(this.jobTitle);
        dest.writeString(this.jobDesc);
        dest.writeString(this.country);
        dest.writeString(this.jobType);
        dest.writeInt(this.salary);
        dest.writeString(this.benefits);
    }

    protected Job(Parcel in) {
        companyName = in.readString();
        jobTitle = in.readString();
        jobDesc = in.readString();
        country = in.readString();
        jobType = in.readString();
        salary = in.readInt();
        benefits = in.readString();
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };
}
