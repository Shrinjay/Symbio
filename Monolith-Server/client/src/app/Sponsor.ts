//Interface defining a sponsor object
export interface Sponsor {
    _id: string, 
    _sponsorname: string,
    _contactname: string,
   _contactemail: string,
    _status: string, 
    _sponsoractions: any,
    _image: string,
    expandActions: boolean
}