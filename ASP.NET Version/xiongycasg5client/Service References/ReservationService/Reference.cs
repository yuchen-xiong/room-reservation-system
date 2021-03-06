﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.18408
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace xiongycasg5client.ReservationService {
    using System.Runtime.Serialization;
    using System;
    
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Runtime.Serialization", "4.0.0.0")]
    [System.Runtime.Serialization.DataContractAttribute(Name="CompositeType", Namespace="http://schemas.datacontract.org/2004/07/xiongycasg5server")]
    [System.SerializableAttribute()]
    public partial class CompositeType : object, System.Runtime.Serialization.IExtensibleDataObject, System.ComponentModel.INotifyPropertyChanged {
        
        [System.NonSerializedAttribute()]
        private System.Runtime.Serialization.ExtensionDataObject extensionDataField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private bool BoolValueField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private string StringValueField;
        
        [global::System.ComponentModel.BrowsableAttribute(false)]
        public System.Runtime.Serialization.ExtensionDataObject ExtensionData {
            get {
                return this.extensionDataField;
            }
            set {
                this.extensionDataField = value;
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public bool BoolValue {
            get {
                return this.BoolValueField;
            }
            set {
                if ((this.BoolValueField.Equals(value) != true)) {
                    this.BoolValueField = value;
                    this.RaisePropertyChanged("BoolValue");
                }
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public string StringValue {
            get {
                return this.StringValueField;
            }
            set {
                if ((object.ReferenceEquals(this.StringValueField, value) != true)) {
                    this.StringValueField = value;
                    this.RaisePropertyChanged("StringValue");
                }
            }
        }
        
        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;
        
        protected void RaisePropertyChanged(string propertyName) {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null)) {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(ConfigurationName="ReservationService.IReservationService")]
    public interface IReservationService {
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IReservationService/GetData", ReplyAction="http://tempuri.org/IReservationService/GetDataResponse")]
        string GetData(int value);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IReservationService/GetData", ReplyAction="http://tempuri.org/IReservationService/GetDataResponse")]
        System.Threading.Tasks.Task<string> GetDataAsync(int value);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IReservationService/GetDataUsingDataContract", ReplyAction="http://tempuri.org/IReservationService/GetDataUsingDataContractResponse")]
        xiongycasg5client.ReservationService.CompositeType GetDataUsingDataContract(xiongycasg5client.ReservationService.CompositeType composite);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IReservationService/GetDataUsingDataContract", ReplyAction="http://tempuri.org/IReservationService/GetDataUsingDataContractResponse")]
        System.Threading.Tasks.Task<xiongycasg5client.ReservationService.CompositeType> GetDataUsingDataContractAsync(xiongycasg5client.ReservationService.CompositeType composite);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IReservationService/ListCustomers", ReplyAction="http://tempuri.org/IReservationService/ListCustomersResponse")]
        string[] ListCustomers();
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IReservationService/ListCustomers", ReplyAction="http://tempuri.org/IReservationService/ListCustomersResponse")]
        System.Threading.Tasks.Task<string[]> ListCustomersAsync();
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IReservationService/ListAvailable", ReplyAction="http://tempuri.org/IReservationService/ListAvailableResponse")]
        string[] ListAvailable(System.DateTime dateIn, System.DateTime dateOut);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IReservationService/ListAvailable", ReplyAction="http://tempuri.org/IReservationService/ListAvailableResponse")]
        System.Threading.Tasks.Task<string[]> ListAvailableAsync(System.DateTime dateIn, System.DateTime dateOut);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IReservationService/Book", ReplyAction="http://tempuri.org/IReservationService/BookResponse")]
        double Book(int customerID, int roomNo, System.DateTime dateIn, System.DateTime dateOut);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IReservationService/Book", ReplyAction="http://tempuri.org/IReservationService/BookResponse")]
        System.Threading.Tasks.Task<double> BookAsync(int customerID, int roomNo, System.DateTime dateIn, System.DateTime dateOut);
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public interface IReservationServiceChannel : xiongycasg5client.ReservationService.IReservationService, System.ServiceModel.IClientChannel {
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public partial class ReservationServiceClient : System.ServiceModel.ClientBase<xiongycasg5client.ReservationService.IReservationService>, xiongycasg5client.ReservationService.IReservationService {
        
        public ReservationServiceClient() {
        }
        
        public ReservationServiceClient(string endpointConfigurationName) : 
                base(endpointConfigurationName) {
        }
        
        public ReservationServiceClient(string endpointConfigurationName, string remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public ReservationServiceClient(string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public ReservationServiceClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(binding, remoteAddress) {
        }
        
        public string GetData(int value) {
            return base.Channel.GetData(value);
        }
        
        public System.Threading.Tasks.Task<string> GetDataAsync(int value) {
            return base.Channel.GetDataAsync(value);
        }
        
        public xiongycasg5client.ReservationService.CompositeType GetDataUsingDataContract(xiongycasg5client.ReservationService.CompositeType composite) {
            return base.Channel.GetDataUsingDataContract(composite);
        }
        
        public System.Threading.Tasks.Task<xiongycasg5client.ReservationService.CompositeType> GetDataUsingDataContractAsync(xiongycasg5client.ReservationService.CompositeType composite) {
            return base.Channel.GetDataUsingDataContractAsync(composite);
        }
        
        public string[] ListCustomers() {
            return base.Channel.ListCustomers();
        }
        
        public System.Threading.Tasks.Task<string[]> ListCustomersAsync() {
            return base.Channel.ListCustomersAsync();
        }
        
        public string[] ListAvailable(System.DateTime dateIn, System.DateTime dateOut) {
            return base.Channel.ListAvailable(dateIn, dateOut);
        }
        
        public System.Threading.Tasks.Task<string[]> ListAvailableAsync(System.DateTime dateIn, System.DateTime dateOut) {
            return base.Channel.ListAvailableAsync(dateIn, dateOut);
        }
        
        public double Book(int customerID, int roomNo, System.DateTime dateIn, System.DateTime dateOut) {
            return base.Channel.Book(customerID, roomNo, dateIn, dateOut);
        }
        
        public System.Threading.Tasks.Task<double> BookAsync(int customerID, int roomNo, System.DateTime dateIn, System.DateTime dateOut) {
            return base.Channel.BookAsync(customerID, roomNo, dateIn, dateOut);
        }
    }
}
