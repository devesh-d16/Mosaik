import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogHeader,
  DialogTrigger,
} from "@/components/ui/dialog";
import { ScrollArea } from "@/components/ui/scroll-area";
import { PlusIcon } from "@radix-ui/react-icons";
import InviteUserForm from "./InviteUserForm";
import IssueList from "./IssueList";
import ChatBox from "./ChatBox";
import { useDispatch, useSelector } from "react-redux";
import { fetchProjectByID } from "@/Redux/Project/Action";
import { useEffect } from "react";
import { useParams } from "react-router-dom";

const ProjectDetails = () => {
  const dispatch = useDispatch();
  const projectDetails = useSelector((store) => store.project.projectDetails); // Select only projectDetails
  const { id } = useParams();

  useEffect(() => {
    if (id) {
      dispatch(fetchProjectByID(id));
    }
  }, [id, dispatch]);

  return (
    <div className="mt-5 lg:px-10">
      <div className="lg:flex gap-5 justify-between pb-4">
        <ScrollArea className="h-screen lg:w-[69%] pr-2">
          <div className="text-gray-400 pb-10 w-full">
            <h1 className="text-lg font-semibold pb-5">
              {projectDetails?.name}
            </h1>
            <div className="space-y-5 pb-10 text-sm">
              <p className="w-full md:max-w-lg lg:max-w-xl">
                {projectDetails?.description}
              </p>
              <div className="flex">
                <p className="w-36">Project Lead :</p>
                <p>{projectDetails?.owner?.fullName}</p>
              </div>

              <div className="flex">
                <p className="w-36">Members :</p>
                <div className="flex items-center gap-2">
                  {projectDetails?.team?.map((item) => (
                    <Avatar className="cursor-pointer" key={item.id}>
                      <AvatarFallback>{item.fullName[0]}</AvatarFallback>
                    </Avatar>
                  ))}
                  <Dialog>
                    <DialogTrigger asChild>
                      <Button size="sm" variant="outline" className="ml-2">
                        <span>Invite</span>
                        <PlusIcon className="w-3 h-3" />
                      </Button>
                    </DialogTrigger>
                    <DialogContent>
                      <DialogHeader>Invite User</DialogHeader>
                      <InviteUserForm />
                      <Button asChild>
                        <DialogClose />
                      </Button>
                    </DialogContent>
                  </Dialog>
                </div>
              </div>

              <div className="flex">
                <p className="w-36">Category :</p>
                <p>{projectDetails?.category}</p>
              </div>

              <div className="flex">
                <p className="w-36">Status :</p>
                <Badge>{projectDetails?.status}</Badge>
              </div>
            </div>

            <section>
              <p className="py-5 border-b text-lg tracking-wider">Tasks</p>
              <div className="lg:flex md:flex gap-3 justify-between py-5">
                <IssueList status="pending" title="To-Do List" />
                <IssueList status="in_progress" title="In Progress" />
                <IssueList status="done" title="Done" />
              </div>
            </section>
          </div>
        </ScrollArea>
        <div className="lg:w-[30%] rounded-md right-5 top-7">
          <ChatBox />
        </div>
      </div>
    </div>
  );
};

export default ProjectDetails;
