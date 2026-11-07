class Solution {
    static class Server {
        int index;
        int wt;

        Server(int i, int w) {
            this.index = i;
            this.wt = w;
        }
    }

    static class Usage {
        int freeTime;
        Server server;

        Usage(int f, Server s) {
            this.server = s;
            this.freeTime = f;
        }
    }

    PriorityQueue<Server> pqs = new PriorityQueue<>((a, b) -> {
        if (a.wt == b.wt)
            return a.index - b.index;
        return a.wt - b.wt;
    });
    PriorityQueue<Usage> busy = new PriorityQueue<>((a, b) -> a.freeTime - b.freeTime);

    public int[] assignTasks(int[] servers, int[] tasks) {
        for (int i = 0; i < servers.length; i++) 
            pqs.add(new Server(i, servers[i]));
        int[] answer = new int[tasks.length];
        int time = 0;
        for (int i = 0; i < tasks.length; i++) {
            while (!busy.isEmpty() && busy.peek().freeTime <= i) 
                pqs.add(busy.poll().server);
            if (pqs.isEmpty()) {
                time = busy.peek().freeTime;
                while (!busy.isEmpty() && busy.peek().freeTime <= time)
                    pqs.add(busy.poll().server);
            }

            Server server = pqs.poll();
            answer[i] = server.index;
            busy.add(new Usage(time + tasks[i], server));
            if (time == i)
                time++;
        }
        return answer;
    }

}