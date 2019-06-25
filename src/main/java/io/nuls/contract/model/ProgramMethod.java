package io.nuls.contract.model;

import java.util.List;

public class ProgramMethod {

    private String name;

    private String desc;

    private List<ProgramMethodArg> args;

    private String returnArg;

    private boolean view;

    private boolean event;

    private boolean payable;

    public ProgramMethod() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ProgramMethodArg> getArgs() {
        return args;
    }

    public void setArgs(List<ProgramMethodArg> args) {
        this.args = args;
    }

    public String getReturnArg() {
        return returnArg;
    }

    public void setReturnArg(String returnArg) {
        this.returnArg = returnArg;
    }

    public boolean isView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }

    public boolean isEvent() {
        return event;
    }

    public void setEvent(boolean event) {
        this.event = event;
    }

    public boolean isPayable() {
        return payable;
    }

    public void setPayable(boolean payable) {
        this.payable = payable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProgramMethod that = (ProgramMethod) o;

        if (view != that.view) {
            return false;
        }
        if (event != that.event) {
            return false;
        }
        if (payable != that.payable) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) {
            return false;
        }
        if (args != null ? !args.equals(that.args) : that.args != null) {
            return false;
        }
        return returnArg != null ? returnArg.equals(that.returnArg) : that.returnArg == null;
    }

    public String[] argsType2Array() {
        if (args != null && args.size() > 0) {
            int size = args.size();
            String[] result = new String[size];
            for (int i = 0; i < size; i++) {
                result[i] = args.get(i).getType();
            }
            return result;
        } else {
            return null;
        }
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (args != null ? args.hashCode() : 0);
        result = 31 * result + (returnArg != null ? returnArg.hashCode() : 0);
        result = 31 * result + (view ? 1 : 0);
        result = 31 * result + (event ? 1 : 0);
        result = 31 * result + (payable ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProgramMethod{" +
                "name=" + name +
                ", desc=" + desc +
                ", args=" + args +
                ", returnArg=" + returnArg +
                ", view=" + view +
                ", event=" + event +
                ", payable=" + payable +
                '}';
    }
}
