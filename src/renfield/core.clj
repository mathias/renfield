(ns renfield.core)

(defplan reduce-user-busyness-by-delegating-task
  [task]
  (where (and (> user-busyness user-busyness-threshold)
              (delegatable-task?))
         (create-candidate-goal :suggest-user-delegates task)))

(defplan commence-next-step-of-shared-workflow
  [task]
  (where (and (shared-workflow-exists? task)
              (task-completed? task)
              (task-has-subtask? task)
              (task-can-be-handled-by-renfield? task))
         (create-candidate-goal :suggest-renfield-commence task)))

(defplan notify-user-of-potential-syenrgy
  [task]
  (where (and (task-unfinished? task)
              (task-has-related-task? task))
         (create-candidate-goal :notify-potential-synergy task)))

(defplan notify-user-of-change-to-schedule
  [task]
  (where (and (task-upcoming? task)
              (task-changed-scheduling? task))
         (create-candidate-goal :notify-user-of-change task)))

(defplan renfield-can-notify-of-vacation
  [email-event]
  (where (and (user-on-vacation?)
              (email-unread? email-event))
         (create-candidate-goal :reply-with-vacation-reminder email-event)))
