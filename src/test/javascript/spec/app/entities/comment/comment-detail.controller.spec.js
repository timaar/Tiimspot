'use strict';

describe('Comment Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockComment, MockEvent, MockOuder, MockPersoon, MockPersoonEvent;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockComment = jasmine.createSpy('MockComment');
        MockEvent = jasmine.createSpy('MockEvent');
        MockOuder = jasmine.createSpy('MockOuder');
        MockPersoon = jasmine.createSpy('MockPersoon');
        MockPersoonEvent = jasmine.createSpy('MockPersoonEvent');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Comment': MockComment,
            'Event': MockEvent,
            'Ouder': MockOuder,
            'Persoon': MockPersoon,
            'PersoonEvent': MockPersoonEvent
        };
        createController = function() {
            $injector.get('$controller')("CommentDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'tiimspotApp:commentUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
